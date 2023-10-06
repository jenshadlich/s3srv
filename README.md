# s3srv

![build](https://github.com/jenshadlich/s3srv/actions/workflows/maven.yml/badge.svg)

S3-compatible server written in Java. The compatiblity is currently limited of course as it's still work in progress :grimacing:

## What currently works:
* create, delete, check for existence and list buckets and objects (just 1000)
  * mostly the happy path
  * single user
* path-style access and virtual hosting (beta)

## Roadmap

* implement multipart upload
* test compatibility: https://github.com/ceph/s3-tests
* support delimiter=%2F&encoding-type=url (commonPrefix)

### Ideas

* different backends
  * memory (currently the only one)
  * file, different databases
