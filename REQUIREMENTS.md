# Android Technical Challenge

Thank you for taking the time to complete this assessment. Please take as much time as you feel is necessary to demonstrate your ability. Things we would like to see: **LiveData, Coroutines, Jetpack's Navigation, and MVVM**. We use GraphQL and Apollo which are set up in the project. 

The app works with a GraphQL backend and we have provided everything you need to interact with it using the Apollo library. This library will automatically generate some code for you based on the GraphQL files each time the project is built, so make sure to build it after adding or changing .graphql files to access your changes.

Base project with dependencies are set up. Please have this forked and running before live code challenge: https://github.com/podium/Android-Challenge


## Project Description
Your task is to build an application around a list of popular movies. The backend is a GraphQL API, which is available at: https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql

## Requirements
- [x] Create a view with the following sections:
  - [x] “Movies: Top 5”: Lists the top 5 movies of the data set, according to rating.
  - [x] “Browse by Genre”: Lists available genres.
  - [x] “Browse by All”: Lists available movies.
- [x] Pressing a movie navigates to a detailed view of the movie.
  - [x] Include title, rating, genres, poster, and description
  - [x] List the cast
  - [x] List the director
- [x] Pressing a genre navigates to a new view showing the category and associated movies.
- [x] The “Browse by All” and Genre view allows the user to sort the list of movies by an order of their choice (i.e. popularity).

### Optional
Once the required steps are completed and if you'd like to further showcase your skills, you may optionally choose from any of the following:

- [ ] Add a search bar that allows searching on 2 or more fields of the movie object.
- [ ] Add pagination.
- [ ] Lazy load the images of the movie item component so they only appear once the component is visible.
- [ ] Add at least one chart or graph representing anything you feel is helpful to the end user.
- [ ] Add functionality where clicking on an image preview in the first section expands the image in a modal.
- [ ] Add any custom feature you think would benefit the end user.

## Final Notes
Please commit and check-in frequently

Feel free to use any third-party libraries, frameworks, etc.

This app is yours. Any changes to any part of the app are more than welcome!
