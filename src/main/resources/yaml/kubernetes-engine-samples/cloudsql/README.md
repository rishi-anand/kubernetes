## Using Google Cloud SQL from a WordPress Deployment

This example shows how to connect to
[Google Cloud SQL](https://cloud.google.com/sql/docs/) from an application
running on [Google Kubernetes Engine](https://cloud.google.com/kubernetes-engine).

The [`mysql_wordpress_deployment.yaml` manifest file](mysql_wordpress_deployment.yaml)
that consists of two containers:

- A web frontend container running WordPress.
- A sidecar container for
  [Cloud SQL Proxy](https://github.com/GoogleCloudPlatform/cloudsql-proxy/)
  container providing connectivity to Cloud SQL.

> :warning: **NOTE:** This example does not provide a WordPress example that is
ready to use in production, as it does not configure a persistent disk for the
WordPress set up and may yield in data loss.
> Follow the [Using Persistent Disks with WordPress and
MySQL](https://cloud.google.com/kubernetes-engine/docs/tutorials/persistent-disk) for an example that is ready to use.

If you are looking for a PostgreSQL example, see [`postgres_deployment.yaml`](postgres_deployment.yaml).

### Prerequisites

Follow the tutorial at [Connecting from Google Container
Engine](https://cloud.google.com/sql/docs/mysql/connect-kubernetes-engine).

After you follow the tutorial, you must have Secrets named
`cloudsql-instance-credentials` and `cloudsql-db-credentials` in your cluster:

```
$ kubectl get secrets
NAME                            TYPE                                  DATA      AGE
cloudsql-db-credentials         Opaque                                2         33m
cloudsql-instance-credentials   Opaque                                1         35m
```

### Deploy WordPress with Cloud SQL

Open `mysql_wordpress_deployment.yaml` and modify `<INSTANCE_CONNECTION_NAME>` with
your the connection name of your Cloud SQL instance.

Then apply the manifest:

```sh
$ kubectl apply -f mysql_wordpress_deployment.yaml
deployment "wordpress" created
```

After a while, you should see a Pod with two containers is "Running":

```sh
$ kubectl get pods
NAME                         READY     STATUS    RESTARTS   AGE
wordpress-1615343444-d95v3   2/2       Running   0          1m
```

This means the WordPress application is able to connect the Cloud SQL proxy.
Follow the next step to expose the WordPress container with a load balancer and
set up the blog to verify the connection to the Cloud SQL instance.

If the Pod fails to start, consider troubleshooting using commands:
- `kubectl describe deployment wordpress`
- `kubectl logs -l app=wordpress web`
- `kubectl logs -l app=wordpress cloufsq`


### Expose the WordPress deployment

The following command will create a public IP and load balancer for the
WordPress deployment.

```
$ kubectl expose deployment wordpress --type=LoadBalancer
service "wordpress" exposed
```

Run `kubectl get services` and wait until the `wordpress` service gets a value
for `EXTERNAL-IP`.

Then visit this IP address and complete setting up the blog. If you succeed,
it means the WordPress container is successfully using the Cloud SQL instance.

### Cleanup

The following command will delete the Deployment and Service resources:

```
$ kubectl delete service,deployment -l app=wordpress
```
