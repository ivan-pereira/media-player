# media-player

Given the time constraints of 3 days, the app is not completely functional. But the overall architecture is in place, some implementation detail related to the sensors still needs to be adjusted to be fully complaint with the requirements

**Details about the implementation:**

 - I've used Android Hilt to resolve dependency injection
   
 - Gyroscope events are abstracted in a classes using callback flow
   which    will dispose the reference once it's not longer in use

   

 - To facilitate the video player, the application is defaulted to   
   landscape

   

 - For simplicity some cases were not handled for example: permissions  
   request or whether or not the sensors exists or not

   

 - Unit tests are not handled even tho the architecture and classes are 
   designed with unit test in mind - That means using mostly interfaces 
   and injecting them into use cases as well as decouple domain classes 
   from the Android framework as much as possible

