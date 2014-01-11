# The `cite` library #


## What it is and what's here
Cite is a library for working CTS URNs, CTS TextInventories, and  CITE Object URNs.

## Building and using
This library uses gradle ([http://www.gradle.org/](http://www.gralde.org/)) to build the
library and documentation, and to run unit tests.

To see available tasks, run `gradle tasks`

The build file included in this distribution uses three properties named nexusRepo, nexusUser and nexusPassword to upload artifacts to a Nexus repository manager.  You must set these properties (e.g., ina gradle.properties file, or on the command line) to use the uploadArchives task.

## Copyright and license

The CITE library is copyright (c) 2008-2014 Neel Smith, and  is licensed under the GNU General Public License version 3 ([http://www.gnu.org/licenses/gpl.html](http://www.gnu.org/licenses/gpl.html)).

