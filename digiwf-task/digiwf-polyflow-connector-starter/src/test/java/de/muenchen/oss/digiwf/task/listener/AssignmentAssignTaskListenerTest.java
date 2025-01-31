package de.muenchen.oss.digiwf.task.listener;

import de.muenchen.oss.digiwf.task.TaskManagementProperties;
import de.muenchen.oss.digiwf.task.TaskVariables;
import lombok.val;
import org.assertj.core.api.AssertionsForClassTypes;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.community.mockito.delegate.DelegateTaskFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssignmentAssignTaskListenerTest {

  private final ProcessEngineConfigurationImpl processConfiguration = mock(ProcessEngineConfigurationImpl.class);
  private final TaskManagementProperties.AssignmentProperties properties = Mockito.mock(TaskManagementProperties.AssignmentProperties.class);
  private final AssignmentAssignTaskListener assignmentAssignTaskListener = new AssignmentAssignTaskListener(properties, processConfiguration);

  private DelegateTaskFake delegateTask;

  @BeforeEach
  void setup_task() {
    delegateTask = new DelegateTaskFake().withAssignee("assignee").withId(UUID.randomUUID().toString());
    delegateTask.addCandidateGroup("candidateGroup1");
    delegateTask.addCandidateGroup("candidateGroup2");
    delegateTask.addCandidateUser("candidateUser1");
    delegateTask.addCandidateUser("candidateUser2");
  }


  @Test
  public void is_disabled_by_properties() {
    when(properties.isShadow()).thenReturn(false);

    val event = assignmentAssignTaskListener.taskAssigned(delegateTask);
    assertThat(delegateTask.getVariables()).isEmpty();
    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    assertThat(DelegateTaskFake.candidateUserIds(delegateTask)).containsExactlyInAnyOrder("candidateUser1", "candidateUser2");
    assertThat(DelegateTaskFake.candidateGroupIds(delegateTask)).containsExactlyInAnyOrder("candidateGroup1", "candidateGroup2");
    assertThat(event).isNull();
  }

  @Test
  public void shadows_assignee_local() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(true);

    val event = assignmentAssignTaskListener.taskAssigned(delegateTask);

    assertThat(delegateTask.getVariablesLocal()).containsEntry(TaskVariables.TASK_ASSIGNEE.getName(), "assignee");
    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    assertThat(event).isNotNull();
    assertThat(event.getAssignee()).isEqualTo("assignee");
  }

  @Test
  public void shadows_assignee_global() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(false);

    val event = assignmentAssignTaskListener.taskAssigned(delegateTask);

    assertThat(delegateTask.getVariables()).containsEntry(TaskVariables.TASK_ASSIGNEE.getName(), "assignee");

    assertThat(delegateTask.getAssignee()).isEqualTo("assignee");
    AssertionsForClassTypes.assertThat(event).isNotNull();
    AssertionsForClassTypes.assertThat(event.getAssignee()).isEqualTo("assignee");

  }

  @Test
  public void shadows_assignee_local_and_deletes_from_task() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(true);
    when(properties.isDelete()).thenReturn(true);

    val event = assignmentAssignTaskListener.taskAssigned(delegateTask);

    assertThat(delegateTask.getVariablesLocal()).containsEntry(TaskVariables.TASK_ASSIGNEE.getName(), "assignee");
    assertThat(delegateTask.getAssignee()).isNull();
    assertThat(event).isNotNull();
    assertThat(event.getAssignee()).isEqualTo("assignee");
  }

  @Test
  public void sets_empty_assignee_to_null_with_empty_assignee() {
    when(properties.isShadow()).thenReturn(true);
    when(properties.isLocal()).thenReturn(true);
    delegateTask.setAssignee("");

    assignmentAssignTaskListener.taskAssigned(delegateTask);

    assertThat(delegateTask.getVariablesLocal()).containsEntry(TaskVariables.TASK_ASSIGNEE.getName(), null);

    assertThat(delegateTask.getAssignee()).isEqualTo("");

  }

}
