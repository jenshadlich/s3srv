# s3srv

![build](https://api.travis-ci.org/jenshadlich/s3srv.svg)

S3-compatible server written in Java.

## What currently works:
* create, delete, check for existence and list buckets and objects
  * mostly the happy path
  * without any users / keys / access restrictions (but this will come)

## Roadmap

* implement signatures V2
  * single user (via config or auto-generated on startup)
* implement multipart upload
* test compatibility: https://github.com/ceph/s3-tests

### Ideas

* different backends
  * memory (currently the only one)
  * file, different databases
