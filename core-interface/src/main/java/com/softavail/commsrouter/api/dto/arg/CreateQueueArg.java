package com.softavail.commsrouter.api.dto.arg;

/**
 * Created by @author mapuo on 05.09.17.
 */
public class CreateQueueArg {

  private String description;
  private String predicate;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPredicate() {
    return predicate;
  }

  public void setPredicate(String predicate) {
    this.predicate = predicate;
  }

}