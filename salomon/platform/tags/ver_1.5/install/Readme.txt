Introduction

    * Core manages and distributes tasks as well as gives environment in which plug-ins operate.
    * Plug-ins are responsible for operating on data, knowledge creation, displaying results etc. They implement logic of the specified task. They can be dynamically loaded and unloaded.

Thanks to the core plug-ins are separated from details of implementation and operate on very high level of abstraction using API given by the core.

Interaction with user can be implemented in various ways: as a standalone application, distributed system, agent system, a library which can be used by other programs, etc. This layer(presentation layer) is very flexible and can be easily extended. User can also switch between different presentation views.

Thanks to Salomon we can implement iterative learning model(one of the basic concepts of knowledge mining) which assumes that knowledge created in one iteration can be used for knowledge creation in the other one.

Salomon enables the communication between tasks. For example: we create at the beginning a learning set, first plug-in extracts some rules from this set which are later enhanced by other plug-ins. At the end the last plug-in presents the results to the user.

Tasks can be connected(by pipes), they can create loops and generally they can be organized in a graph. Because database layer is separated from plug-ins, databases do not have to be exactly the same, they only need to have common attributes defined in our API. If we have for example some databases in hospitals which store data about patients, diseases etc. we do not need to synchronize them and change their format, plug-ins can extract knowledge from what is available for them and thanks to distribution of Salomon share with other plug-ins everything they have learned.

Salomon is portable between different operating systems(Windows, Linux, MacOS, Solaris, FreeBSD, etc.). We have also put a lot of effort in ensuring integrity of data, so all operations are atomic and treated as a transaction - if something fails, all changes made by this operation until the failure are rolled back.

