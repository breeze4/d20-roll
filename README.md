# d20

Implementation for http://breeze.ghost.io/d20-roller-microservice/

A dice rolling microservice. Use a GET request to get various legal D&D dice rolls.

`hostname:3000/roll/:denomination/num/:number` returns `:number` of rolls of a dice with number of sides given by `:denomination`

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2014 FIXME
