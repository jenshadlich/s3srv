import boto
import boto.s3.connection
access_key = 'foo'
secret_key = 'bar'
conn = boto.connect_s3(
    aws_access_key_id = access_key,
    aws_secret_access_key = secret_key,
    host = 'localhost',
    port = 8080,
    is_secure=False,
    calling_format = boto.s3.connection.OrdinaryCallingFormat(),
)

bucket = conn.create_bucket('test-bucket')
