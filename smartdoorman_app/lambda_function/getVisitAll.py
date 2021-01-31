import json
import boto3
from boto3.dynamodb.conditions import Key, Attr
from botocore.exceptions import ClientError
dynamodb = boto3.resource('dynamodb', region_name="us-east-1", endpoint_url="http://dynamodb.us-east-1.amazonaws.com")

table = dynamodb.Table('who_tables') 

def lambda_handler(event, context):
    # TODO implement
    response = table.scan()
    re= response['Items']
    
    return re
