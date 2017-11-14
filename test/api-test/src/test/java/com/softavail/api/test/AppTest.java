/*
 * Copyright 2017 SoftAvail, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.softavail.api.test;

import com.softavail.commsrouter.test.api.Queue;
import com.softavail.commsrouter.test.api.Plan;
import com.softavail.commsrouter.test.api.CommsRouterResource;
import com.softavail.commsrouter.test.api.Agent;
import com.softavail.commsrouter.test.api.Task;
import com.softavail.commsrouter.test.api.Router;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import com.softavail.commsrouter.api.dto.arg.CreateAgentArg;
import com.softavail.commsrouter.api.dto.arg.CreatePlanArg;
import com.softavail.commsrouter.api.dto.arg.CreateQueueArg;
import com.softavail.commsrouter.api.dto.arg.CreateRouterArg;
import com.softavail.commsrouter.api.dto.arg.CreateTaskArg;
import com.softavail.commsrouter.api.dto.arg.UpdateTaskArg;
import com.softavail.commsrouter.api.dto.arg.CreateAgentArg;
import com.softavail.commsrouter.api.dto.model.RouterDto;
import com.softavail.commsrouter.api.dto.model.TaskDto;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import com.softavail.commsrouter.api.dto.model.TaskState;
import com.softavail.commsrouter.api.dto.model.ApiObjectId;
import com.softavail.commsrouter.api.dto.model.QueueDto;
import com.softavail.commsrouter.api.dto.model.RouteDto;
import com.softavail.commsrouter.api.dto.model.PlanDto;
import com.softavail.commsrouter.api.dto.model.AgentDto;
import com.softavail.commsrouter.api.dto.arg.UpdateAgentArg;

/**
 * Unit test for simple App.
 */
public class AppTest {

  @BeforeAll
  public static void beforeAll() throws Exception {
    Assumptions.assumeTrue(System.getProperty("autHost") != null, "autHost is set");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void crudRouter() {
    HashMap<CommsRouterResource, String> state = new HashMap<CommsRouterResource, String>();
    Router r = new Router(state);
    ApiObjectId id = r.create(new CreateRouterArg());
    RouterDto router = r.get();
    assertThat(router.getName(), nullValue());
    assertThat(r.list(), hasItems(hasProperty("id", is(id.getId()))));
    r.replace(new CreateRouterArg());
    r.update(new CreateRouterArg());
    r.delete();
    r.delete();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void crdQueue() {
    HashMap<CommsRouterResource, String> state = new HashMap<CommsRouterResource, String>();
    Router r = new Router(state);
    r.create(new CreateRouterArg());

    Queue q = new Queue(state);
    ApiObjectId id = q.create(new CreateQueueArg.Builder().predicate("1==1").build());
    QueueDto queue = q.get();
    assertThat(queue.getDescription(), nullValue());
    assertThat(q.list(), hasItems(hasProperty("id", is(id.getId()))));
    q.replace(new CreateQueueArg.Builder().predicate("2==2").build());
    q.update(new CreateQueueArg.Builder().predicate("1==1").build());
    q.delete();
    r.delete();
  }

  //@Test // fail when rules is null
  @SuppressWarnings("unchecked")
  public void crdPlan() {
    HashMap<CommsRouterResource, String> state = new HashMap<CommsRouterResource, String>();
    Router r = new Router(state);
    Plan p = new Plan(state);
    ApiObjectId id = r.create(new CreateRouterArg());
    Queue q = new Queue(state);
    ApiObjectId queueId = q.create(new CreateQueueArg.Builder().predicate("true").build());
    CreatePlanArg arg = new CreatePlanArg();
    RouteDto defaultRoute = new RouteDto();
    defaultRoute.setQueueId(queueId.getId());
    arg.setDefaultRoute(defaultRoute);
    id = p.create(arg);
    PlanDto resource = p.get();
    assertThat(resource.getDescription(), nullValue());
    assertThat(p.list(), hasItems(hasProperty("id", is(id.getId()))));
    p.replace(arg);
    p.update(arg);
    //p.delete(); Known issue
    r.delete();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void crdAgent() {
    HashMap<CommsRouterResource, String> state = new HashMap<CommsRouterResource, String>();
    Router r = new Router(state);
    ApiObjectId id = r.create(new CreateRouterArg());
    Agent a = new Agent(state);
    CreateAgentArg arg = new CreateAgentArg();
    id = a.create(arg);
    AgentDto resource = a.get();
    assertThat(resource.getCapabilities(), nullValue());
    assertThat(a.list(), hasItems(hasProperty("id", is(id.getId()))));
    a.createWithId(new CreateAgentArg());
    a.update(new UpdateAgentArg());
    a.delete();
    r.delete();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void crdTask() throws MalformedURLException {
    HashMap<CommsRouterResource, String> state = new HashMap<CommsRouterResource, String>();
    Router r = new Router(state);
    ApiObjectId id = r.create(new CreateRouterArg());
    Queue q = new Queue(state);
    ApiObjectId queueId = q.create(new CreateQueueArg.Builder().predicate("1==1").build());
    Task t = new Task(state);
    CreateTaskArg arg = new CreateTaskArg();
    arg.setCallbackUrl(new URL("http://example.com"));
    arg.setQueueId(queueId.getId());
    id = t.create(arg);
    TaskDto resource = t.get();
    assertThat(resource.getRequirements(), nullValue());
    assertThat(t.list(), hasItems(hasProperty("id", is(id.getId()))));
    t.replace(new CreateTaskArg.Builder()
              .callback(new URL ("http://localhost:8080"))
              .queue(queueId.getId())
              .build());
    t.update(new UpdateTaskArg.Builder().state(TaskState.completed).build());
    t.delete();
    q.delete();
    r.delete();
  }

}
