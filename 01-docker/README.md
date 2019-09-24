# Docker Demo


## Demo 1: Basics

Let's start by running basic commands.

To pull a Docker image

```
$ docker pull nginx
```

To run ngnix

```
$ docker run -d  nginx
```

To access on local machine, we will have to expose port

```
$ docker run -p 8000:80 nginx
```

To stop a container, we can run following command.

```
$ docker stop <container_id>
```

To remove container

```
$ docker rm <contaner_id>
```

You can run a container in background by passing it `-d` option.

```
$ docker run -p 8000:80 -d nginx
```

You can view logs using the following command.

```
$ docker logs -f container_id
```

To view all the running containers

```
$ docker ps
```

To view all the containers irrespective of their status

```
$ docker ps --all
```

To remove all the stopped containers

```
$ docker container prune
```

## Demo 2: Running application

This demo has two parts

1. Application without Redis
2. Application with Redis

> You don't have to type `$` . `$`  signifies command prompt.

### Application without Redis Demo

Start by demoing the application and showing the `Dockerfile`.

You can build the application and build Docker image

```
$ ./gradlew clean build docker
```

Once the process completes, view the built image using following command.

```
$ docker images | grep image-resolver-api
```

You can run the image using the following command.

```
$ docker run -p 8080:8080 image-resolver-api
```

The application will be accessible at http://localhost:8080

```
$ curl http://localhost:8080/api/v1/images?url=https://medium.com/@angelquicksey/service-design-for-policy-b0a9408dced1
```

Each time you will make a call you will hit the internet and download the HTML of the page. On each request, you will see following line in the log. If you make 4 calls for the same URL you will get see four lines.

```
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
```

Stop the container by `Ctrl + C` or `docker stop <container_id>`.

### Application with Redis

Isn't it will be better if we could cache the images? If users makes request for the same URL then we should not do the computation. Computation is expensive.

Let's pull redis image.

```
$ docker pull redis
```

Now we can run the redis by running following command.

```
$ docker run --name redis -d redis
```

We can run our application container with caching enabled by running following command.

```
$ docker run -p 8080:8080 -e IMAGE_CACHE_ENABLED=true image-resolver-api
```

If you hit the API URL you will be greeted with HTTP 500 Internal Server Error. The logs will have following

```
2019-08-25 16:57:54.711 ERROR 1 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to localhost:6379] with root cause

java.net.ConnectException: Connection refused
```

**The problem is that redis is not available on localhost**

Docker allows containers to access each other by name if they are part of the same network.

```
$ docker network create mynet
```

Stop both the containers.

```
$ docker container prune
```

To remove all stopped containers run the following command.

```
$ docker rm $(docker ps -q --all)
```

Now, we will run the containers in the `mynet` network.

```
$ docker run --name redis --net mynet -d redis
```

```
$ docker run -p 8080:8080 -e IMAGE_CACHE_ENABLED=true -e SPRING_REDIS_HOST=redis --net mynet image-resolver-api
```

Now if you make repeated calls to the API, only first request for a URL will be served from the network rest will be served from the cache.

```
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
```

### Using redis-cli to see data

```
$ docker run -it --network mynet --rm redis redis-cli -h redis
```

### Demo 3: Volumes

#### Using --mount option

Bind Mount
```
Bind a volume
docker run -d  -it -p 80:80 --name devtest  --mount type=bind,source="$(pwd)"/ravindra,target=/usr/share/nginx/html nginx:latest
```


Volume
```
Bind a volume
docker volume create hello
docker volume inspect hello
cd /var/lib/docker/volumes/hello
cp /home/ravindrashekhawat5876/demo/k8s-workshop/01-docker/ravindra/* . -r
docker run -d  -it -p 80:80 --name devtest  --mount type=volume,source=hello,target=/usr/share/nginx/html nginx:latest
docker stop devtest
docker container prune
docker volume rm  hello
```

#### Using --volume option
Bind Mount
```
docker run -d  -it -p 80:80 --name devtest  -v "$(pwd)"/ravindra:/usr/share/nginx/html nginx:latest

Multiple Volumes:-
docker run -d  -it -p 80:80 --name devtest  -v "$(pwd)"/ravindra:/usr/share/nginx/html  -v "$(pwd)"/logs:/var/log/nginx  nginx:latest
 ```
Volume
```
docker volume create hello
docker run -d  -it -p 80:80 --name devtest  -v hello:/usr/share/nginx/html nginx:latest

```

Volume in Dockerfile :-

Volume in dockerfile will create a random Docker Area Volume. It will mount this volume in the container. 
Note:- Any changes in the mounted volume in Dockerfile will be discarded.

