package com.nvg.exam.phannguyen.data.services.api;

import android.os.Build;

import com.nvg.exam.phannguyen.data.BuildConfig;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.IApiGitRepoService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

/**
 * Created by phannguyen on 7/29/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = Build.VERSION_CODES.M)
public class ApiGitRepoServiceImplTest {
    IApiGitRepoService apiGitRepoService;
    MockWebServer mMockWebServer;
    IGitReposWS gitReposWS;
    @Before
    public void setUp() throws Exception {
        mMockWebServer = new MockWebServer();
        gitReposWS = new Retrofit.Builder()
                .baseUrl(mMockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IGitReposWS.class);
        apiGitRepoService = new ApiGitRepoServiceImpl(gitReposWS);
    }

    @After
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }

    @Test
    public void loadGitReposList() throws Exception {
        enqueueResponse("repos.json");
        TestSubscriber<List<? extends IGitRepoModel>> testSubscriber = TestSubscriber.create();
        apiGitRepoService.loadGitReposList(anyInt(),anyInt()).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<? extends IGitRepoModel> resultModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(resultModel.size(), equalTo(5));
    }

    @Test
    public void getDetaiGitRepo() throws Exception {
        enqueueResponse("repodetail.json");
        String repo_name = "agent-plugins";
        TestSubscriber<IGitRepoModel> testSubscriber = TestSubscriber.create();
        apiGitRepoService.getDetaiGitRepo(anyString()).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        IGitRepoModel resultModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(resultModel.getTitle(), equalTo(repo_name));
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mMockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }


}