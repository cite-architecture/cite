package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestWorkLevelEnum {



      def expectedLabels = ["exemplar", "version", "work", "text group"]

      @Test
      void testWorkLevelEnum() {
        ArrayList testList = WorkLevel.values()  as ArrayList


        testList.eachWithIndex { n, i ->
          assert n.getLabel() == expectedLabels[i]
        }

      }

      @Test
      void testIndex() {
        assert WorkLevel.getByLabel("exemplar") == WorkLevel.EXEMPLAR
        assert WorkLevel.getByLabel("version") == WorkLevel.VERSION
        assert WorkLevel.getByLabel("work") == WorkLevel.WORK
        assert WorkLevel.getByLabel("text group") == WorkLevel.GROUP
      }


}
