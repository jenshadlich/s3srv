import boto.s3.connection

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

from boto.s3.bucket import Bucket

bucket = Bucket(conn, 'test-bucket')

bucket.delete_key('test-object-1')
