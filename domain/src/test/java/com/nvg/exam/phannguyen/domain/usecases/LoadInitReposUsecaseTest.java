package com.nvg.exam.phannguyen.domain.usecases;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.IApiGitRepoService;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by phannguyen on 7/28/17.
 */
public class LoadInitReposUsecaseTest {
    LoadInitReposUsecase mUsecase;
    @Mock
    IApiGitRepoService apiGitRepoService;
    @Mock
    ILocalGitRepoService localGitRepoService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mUsecase = new LoadInitReposUsecase(apiGitRepoService, localGitRepoService);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(apiGitRepoService, localGitRepoService);
        mUsecase = null;
    }

    @Test
    public void testLoadInitGitRepos_FromLocalService_success() throws Exception {
        IGitRepoModel repo = mock(IGitRepoModel.class);
        List<IGitRepoModel> mockList = Arrays.asList(repo, repo, repo, repo, repo);
        doReturn(Observable.just(mockList)).when(localGitRepoService).getGitRepos(anyInt());
        mUsecase.loadInitRepos().test()
                .assertValueCount(1)
                .assertValue(mockList)
                .assertNoErrors()
                .assertCompleted();
        verify(apiGitRepoService,never()).loadGitReposList(anyInt(),anyInt());
    }

    @Test
    public void testLoadInitGitRepos_FromAPIService_success() throws Exception {
        IGitRepoModel repo = mock(IGitRepoModel.class);
        List<IGitRepoModel> mockList = Arrays.asList(repo, repo, repo, repo, repo);
        doReturn(Observable.just(null)).when(localGitRepoService).getGitRepos(anyInt());
        doReturn(Observable.just(mockList)).when(apiGitRepoService).loadGitReposList(anyInt(),anyInt());
        doReturn(Observable.just(null)).when(localGitRepoService).saveGitRepo(anyListOf(IGitRepoModel.class));

        mUsecase.loadInitRepos().test()
                .assertValueCount(1)
                .assertValue(mockList)
                .assertNoErrors()
                .assertCompleted();

        verify(localGitRepoService, times(1)).getGitRepos(anyInt());
        verify(apiGitRepoService, times(1)).loadGitReposList(anyInt(),anyInt());
        verify(localGitRepoService, times(1)).saveGitRepo(eq(mockList));
    }

    @Test
    public void testLoadInitGitRepos_FromLocalService_Error() throws Exception {
        doReturn(Observable.error(new Exception())).when(localGitRepoService).getGitRepos(anyInt());
        IGitRepoModel repo = mock(IGitRepoModel.class);
        List<IGitRepoModel> mockList = Arrays.asList(repo, repo, repo, repo, repo);
        doReturn(Observable.just(mockList)).when(apiGitRepoService).loadGitReposList(anyInt(),anyInt());
        doReturn(Observable.just(null)).when(localGitRepoService).saveGitRepo(anyListOf(IGitRepoModel.class));
        mUsecase.loadInitRepos().test()
                .assertError(Exception.class);

        verify(localGitRepoService, times(1)).getGitRepos(anyInt());
        verify(apiGitRepoService, never()).loadGitReposList(anyInt(),anyInt());
        verify(localGitRepoService, never()).saveGitRepo(anyListOf(IGitRepoModel.class));
    }

    @Test
    public void testLoadInitGitRepos_FromLocalService_Error_FromAPI_Error() throws Exception {
        doReturn(Observable.just(null)).when(localGitRepoService).getGitRepos(anyInt());
        doReturn(Observable.error(new Exception())).when(apiGitRepoService).loadGitReposList(anyInt(),anyInt());
        mUsecase.loadInitRepos().test()
                .assertError(Exception.class);

        verify(localGitRepoService, times(1)).getGitRepos(anyInt());
        verify(apiGitRepoService, times(1)).loadGitReposList(anyInt(),anyInt());
        verify(localGitRepoService, never()).saveGitRepo(anyListOf(IGitRepoModel.class));
    }

}