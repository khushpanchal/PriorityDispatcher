[![](https://jitpack.io/v/khushpanchal/PriorityDispatcher.svg)](https://jitpack.io/#khushpanchal/PriorityDispatcher)

# PriorityDispatcher

Small utility Kotlin library, PriorityDispatcher, that prioritise tasks within coroutines based on a priority queue.

<p align="center">
  <img src=https://github.com/khushpanchal/PriorityDispatcher/blob/master/assets/logo.png >
</p>

### Installation

To integrate PriorityDispatcher library into your Android project, follow these simple steps:

1. Update your settings.gradle file with the following dependency.
   
```Groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // this one
    }
}
```

2. Update your module level build.gradle file with the following dependency.
   
```Groovy
dependencies {
        implementation 'com.github.khushpanchal:PriorityDispatcher:1.0.0'
}
```

### Usage

- Low Level Priority Task

```Kotlin
CoroutineScope(PriorityDispatcher.low()).launch {
    // Task with low priority
}
```
- Medium Level Priority Task

```Kotlin
CoroutineScope(PriorityDispatcher.medium()).launch {
    // Task with medium priority
}
```
- High Level Priority Task

```Kotlin
CoroutineScope(PriorityDispatcher.high()).launch {
    // Task with high priority
}
```

- Immediate Level Priority Task

```Kotlin
CoroutineScope(PriorityDispatcher.immediate()).launch {
    // Task with immediate priority - Runs irrespective of current workload
}
```

## Blog: Check out the blog for more guided and technical details: [Medium Blog](https://medium.com/@khush.panchal123/prioritydispatcher-dispatcher-based-on-priority-queue-035cebd6f96a)

### Contact Me

- [LinkedIn](https://www.linkedin.com/in/khush-panchal-241098170/)
- [Twitter](https://twitter.com/KhushPanchal15)
- [Gmail](mailto:khush.panchal123@gmail.com)

### If this project helps you, show love ❤️ by putting a ⭐ on [this](https://github.com/khushpanchal/PriorityDispatcher) project ✌️

### Contribute to the project

Feel free to provide feedback, report an issue, or contribute to PriorityDispatcher. Head over to [GitHub repository](https://github.com/khushpanchal/PriorityDispatcher), create an issue or find the pending issue. All pull requests are welcome 😄

### License

```
   Copyright (C) 2024 Khush Panchal

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
