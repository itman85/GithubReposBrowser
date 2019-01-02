# GithubReposBrowser
1.This app has some functions:
- Load and display repositories from github api (not load all repositories at init from api).
- Load more repositories from local first, if no more local data then load from api
- Display repo item info  with owner avatar and repo name.
- Display repo detail info when click onto repo items list.
- Search repositories by name, this will search all repo store in local db.
2.Techniques
- I built this app used CLEAN architecture combine with MVVM pattern.
- Using mockito, mockwebserver to write unit tests for domain layer and data layer
- Using retrofit2 as rest client, room persistence for local db
- Using Dagger 2, Rxjava.
