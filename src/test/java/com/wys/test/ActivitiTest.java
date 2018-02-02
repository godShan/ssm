package com.wys.test;

import org.activiti.engine.*;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ActivitiTest {
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private FormService formService;

    @Test
    public void createUser() {
        User user = identityService.newUser("admin");
        user.setPassword("123456");
        identityService.saveUser(user);

//        User user = identityService.createUserQuery().userId("admin").singleResult();
//        user.setPassword("234567");
//        identityService.saveUser(user);

    }


    @Test
    public void deployProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()  //用于流程定义和部署相关对象的Service
                .createDeployment().enableDuplicateFiltering()   //创建一个部署对象
                .name("授信")
                .category("日常流程")
                .addClasspathResource("diagrams/jg_credit_ps.bpmn") //从ClassPath资源中加载，一次只能加载一个文件
                .addClasspathResource("diagrams/jg_credit_ps.png")  //从ClassPath资源中加载，一次只能加载一个文件
                .deploy();

        System.out.println("deployment" + deployment.getId());   //1
        System.out.println("deployment" + deployment.getName());//部门程序

        //涉及到的表
        //act_ge_bytearray  act_re_deployment  act_re_procdef
    }

    /**
     * 执行流程实例
     */
    @Test
    public void startProcessInstance() {
        //发起流程
        Map<String, Object> variables = new HashMap<String, Object>();
        String processInstanceKey = "leave";
        String businessKey = "leave:1";
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processInstanceKey, businessKey, variables);

        // 部门领导审批通过
//        Map<String, Object> variables = new HashMap<String, Object>();
//        Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
//        variables.put("deptLeaderApproved", "true");
//        taskService.complete(deptLeaderTask.getId(), variables);
        //formService.submitTaskFormData(deptLeaderTask.getId(), variables);

        //Task task= taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee("1").list().get(0);
        // System.out.println("流程实例id：" + pi.getId());  //流程实例id  22501
//	    System.out.println("流程定义id：" + task.getTaskDefinitionKey());   //流程定义ID helloworld:1:4
        //提交
        //taskService.complete(task.getId(), variables);

        //业务总监
    }

    /**
     * 查找个人当前的要执行的任务
     */
    public void findMyTaskInfo() {
        String assignee = "bb";
        List<Task> listTask = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        if (listTask != null && listTask.size() > 0) {
            for (Task task : listTask) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务时间：" + task.getCreateTime());
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务的实例ID：" + task.getProcessDefinitionId());
                System.out.println("执行对象的ID：" + task.getExecutionId());
                System.out.println("流程定义ID：" + task.getProcessInstanceId());
            }
        }


    }


    public void completeMyPersoinTask() {
        String taskId = "12508";
        processEngine.getTaskService()
                .complete(taskId);

        System.out.println("完成任务，任务ID：" + taskId);
    }
}
