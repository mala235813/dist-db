# dist-db
A merge friendly text file based database  written in Clojure.

# Design goals
1. Merge friendly. That is, dist-db can be version controlled in any VCS. It can be forked and merged efforlessly. The risk for a merge conflict is negliable. Should one arise it is trivial to resolve.
2. Use existing techs where possible. Don't reinvent the wheel.

# Non design goals
* Big data
* Multi threaded access
* Minimize storage size
