import boto.s3.connection
from boto.s3.key import Key

access_key = 'GUSCQ627K6CC4OAL4RGC'
secret_key = 'eU8cXNzdXGEFpMMa4SSINjlIeD1fea4pMO3dIw9T'
conn = boto.connect_s3(
    aws_access_key_id=access_key,
    aws_secret_access_key=secret_key,
    host='localhost',
    port=8888,
    is_secure=False,
    calling_format=boto.s3.connection.OrdinaryCallingFormat(),
)

bucket = conn.get_bucket('test-bucket')

key1 = Key(bucket)
key1.key = 'test/object-1'
key1.content_type = 'text/plain'
key1.set_contents_from_string("content 1")

key2 = Key(bucket)
key2.key = 'test-object-2'
key2.content_type = 'text/plain'
key2.set_contents_from_string("content 2")
