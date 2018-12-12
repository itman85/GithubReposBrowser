package com.nvg.exam.phannguyen.data.services.local;

import android.arch.persistence.room.Room;

import com.nvg.exam.phannguyen.data.BuildConfig;
import com.nvg.exam.phannguyen.data.db.AppDB;
import com.nvg.exam.phannguyen.data.models.converters.GitRepoConverter;
import com.nvg.exam.phannguyen.data.models.local.LocalGitRepoModel;
import com.nvg.exam.phannguyen.domain.DomainConstant;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by phannguyen on 7/29/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LocalGitRepoServiceImplTest {
    LocalGitRepoServiceImpl localGitRepoService;
    AppDB appDB;
    GitRepoConverter dataConverter;

    @Before
    public void setUp() throws Exception {
        appDB = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, AppDB.class).build();
        dataConverter = new GitRepoConverter();
        localGitRepoService = new LocalGitRepoServiceImpl(appDB,dataConverter);
    }

    @After
    public void tearDown() throws Exception {
        appDB = null;
        localGitRepoService = null;
    }

    @Test
    public void getGitRepos() throws Exception {
        List<IGitRepoModel> repoModels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            repoModels.add(new LocalGitRepoModel("repo" + i, "Title" + i, "Avatar" + i, "Description" + i, i));
        }

        TestSubscriber<List<? extends IGitRepoModel>> testSubscriber = new TestSubscriber<>();
        localGitRepoService.saveGitRepo(repoModels)
                .subscribeOn(Schedulers.io())
                .flatMap(aVoid -> localGitRepoService.getGitRepos(2).subscribeOn(Schedulers.io()))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<? extends IGitRepoModel> reposList = testSubscriber.getOnNextEvents().get(0);
        assertThat(reposList, notNullValue());
        assertThat(reposList.size(), equalTo(DomainConstant.ITEMS_PER_PAGE_NUMBER));

        IGitRepoModel repo1 = reposList.get(0);
        assertThat(repo1, notNullValue());
        assertThat(repo1.getTitle(), equalTo("Title16"));
        assertThat(repo1.getAvatar(), equalTo("Avatar16"));
        assertThat(repo1.getDetailDescription(), equalTo("Description16"));

    }

    @Test
    public void getDetailGitRepo() throws Exception {
        List<IGitRepoModel> repoModels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            repoModels.add(new LocalGitRepoModel("repo" + i, "Title" + i, "Avatar" + i, "Description" + i, i));
        }

        TestSubscriber<IGitRepoModel> testSubscriber = new TestSubscriber<>();
        localGitRepoService.saveGitRepo(repoModels)
                .subscribeOn(Schedulers.io())
                .flatMap(aVoid -> localGitRepoService.getDetailGitRepo("repo25").subscribeOn(Schedulers.io()))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        IGitRepoModel repo1 = testSubscriber.getOnNextEvents().get(0);
        assertThat(repo1, notNullValue());
        assertThat(repo1.getTitle(), equalTo("Title25"));
        assertThat(repo1.getAvatar(), equalTo("Avatar25"));
        assertThat(repo1.getDetailDescription(), equalTo("Description25"));
    }

}