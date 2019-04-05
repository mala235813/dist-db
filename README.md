# dist-db
A merge friendly text file based key-value store written in clojure

# Design goals
1. Merge friendly. That is, dist-db can be version controlled in any VCS. It can be forked and merged efforlessly. The risk for a merge conflict is negliable. Should one arise it is trivial to resolve.
2. See 1.
3. See 1.
4. Arbitrary values. Values follow the [EDN](https://github.com/edn-format/edn) format.

# Non design goals
* Big data
* Multi threaded access
* Minimize storage size
