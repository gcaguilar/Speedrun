# Speedrun

# Speedrun

This project has taken as its template the following [application](https://github.com/bufferapp/clean-architecture-koin-boilerplate)

I've taken it as a base because it's an approach to Clean Architecture over Android and the MVVM pattern in the UI.

It's over-engineering for such a small project but it allows a strong separation of the different layers.

The cache layer has been eliminated since persistence was not necessary although the over-engineering in data has been maintained. Although the factory pattern has been maintained despite not being necessary with a single data source

The data layer implements the data recovery logic and the business logic, this is due to the scarce logistics that an application usually has.
