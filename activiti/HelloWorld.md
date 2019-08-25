# Activiti 起步.1

工作流引擎，很久之前就想搞一下，这次扫盲一下。
目前，我是下载源码，然后运行 `modules\activiti-ui\start.sh` 在 `http://localhost:9999/activiti-app` 下面画图导出 `bpmn20.xml` 文件。 ProcessEngine 和服务类都是线程安全的，你可以在整个服务器中仅保持它们的一个引用就可以了。

## 创建流程引擎

- 创建一个基于*内存数据库*的流程引擎

```java
  private static ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        Log.info("The engine`s name is [{}] and version at [{}]", processEngine.getName(), ProcessEngine.VERSION);
        return processEngine;
  }

```

## 获取流程定义

```java
 private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        // deploy flow define file
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("second_approve.bpmn20.xml");
        // deploy a process
        Deployment deployment = deploymentBuilder.deploy();
        String deployId = deployment.getId();

        // get processDefinition object base on deployId
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();

        Log.info("flow define file [{}], flow id [{}] ", processDefinition.getName(), processDefinition.getId());
        return processDefinition;
    }

```

- RepositoryService: 提供管理和控制发布包和流程定义的操作。

## 启动并处理流程

```java
 private static void startFlow(ProcessEngine processEngine, ProcessDefinition processDefinition) throws ParseException {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        Log.info("start process Id [{}]", processInstance.getId());

        // Process flow task
        TaskService taskService = processEngine.getTaskService();
        Scanner scanner = new Scanner(System.in);
        while (processInstance != null && !processInstance.isEnded()) {
            List<Task> tasks = taskService.createTaskQuery().list();
            FormService formService = processEngine.getFormService();

            for (Task task : tasks) {
                Log.info("waiting task [{}]", task.getName());
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                // formData properties need
                List<FormProperty> formProperties = taskFormData.getFormProperties();

                Map<String, Object> variables = Maps.newHashMap();
                for (FormProperty property : formProperties) {
                    Log.info("please input [{}]", property.getName());
                    String line = scanner.nextLine();

                    // judge property type
                    if (StringFormType.class.isInstance(property.getType())) {
                        variables.put(property.getId(), line);

                    } else if (DateFormType.class.isInstance(property.getType())) {
                        Log.info("please input {} fellow this format : yyy-mm-dd", property.getName());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dateFormat.parse(line);
                        variables.put(property.getId(), line);
                    } else {
                        Log.info("format is illegal");
                    }

                    Log.info("[{}] is the value of [{}], that you have input", line, property.getName());
                }

                for (Map.Entry entry : variables.entrySet()) {
                    Log.info("The value of {} is {}", entry.getKey(), entry.getValue());
                }

                // process change to next status
                taskService.complete(task.getId(), variables);

                // get new processInstance,if process has hover eng event null will be return
                processInstance = processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .singleResult();

            }
            Log.info("waiting task count [{}]", tasks.size());
        }
    }

```

- taskService: 当前流程中的任务, `processEngine.getFormService()`。
- taskFormData: 当前流程中每个任务的需要的 form 数据, `formService.getTaskFormData(task.getId())`。
