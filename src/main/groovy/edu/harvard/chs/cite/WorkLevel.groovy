package edu.harvard.chs.cite

/** CTS hierarchy of works. */
public enum WorkLevel {

    EXEMPLAR("exemplar"),
    VERSION("version"),
    WORK("work"),
    GROUP("text group")

    private String label
    private WorkLevel(String label) {
      this.label = label
    }

    /** Gets a human-readable label for this value. */
    public String getLabel() {
      return label
    }

    /** Looks up value by labelling String. */
    static getByLabel(String labelStr) {
      values().find { it.label == labelStr }
    }

}
