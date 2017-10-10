package com.softavail.commsrouter.api.interfaces;

import com.softavail.commsrouter.api.dto.arg.CreateTaskArg;
import com.softavail.commsrouter.api.dto.arg.UpdateTaskArg;
import com.softavail.commsrouter.api.dto.arg.UpdateTaskContext;
import com.softavail.commsrouter.api.dto.model.CreatedTaskDto;
import com.softavail.commsrouter.api.dto.model.RouterObjectId;
import com.softavail.commsrouter.api.dto.model.TaskDto;
import com.softavail.commsrouter.api.exception.CommsRouterException;

/**
 * Created by @author mapuo on 04.09.17.
 */
public interface TaskService
    extends RouterObjectService<TaskDto> {

  String X_QUEUE_SIZE = "X-Queue-Size";

  CreatedTaskDto create(CreateTaskArg createArg, String routerId)
      throws CommsRouterException;

  CreatedTaskDto create(CreateTaskArg createArg, RouterObjectId objectId)
      throws CommsRouterException;

  void update(UpdateTaskArg updateArg, RouterObjectId objectId)
      throws CommsRouterException;

  void update(UpdateTaskContext taskContext, RouterObjectId objectId)
      throws CommsRouterException;

}