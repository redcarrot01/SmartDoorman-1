import json
import boto3
from boto3.dynamodb.conditions import Key, Attr
from botocore.exceptions import ClientError


def get_visitor(who_date):
    dynamodb = boto3.resource('dynamodb', region_name="us-east-1", endpoint_url="http://dynamodb.us-east-1.amazonaws.com")

    table = dynamodb.Table('who_tables')


    try:
        response = table.get_item(Key={'who_date': who_date})
    except ClientError as e:
        print(e.response['Error']['Message'])
    else:
        # print("Response!!!!!!!!!!!!" + response)
        print(who_date)
        return response['Item']


def lambda_handler(event, context):
    print('aa')
    print(event)
    print('aa')
    select_query_date = event['queryStringParameters']['who_date']
    print(select_query_date)

    response_db = get_visitor(select_query_date)
    print('aa', response_db)

    if response_db:
        return {
            'statusCode': 200,
            'body': json.dump(response_db)
        }
    else:
        print('uu')
        return {
            'statusCode': 201,
            'body': json.dumps('NO TABLE')
        }


if __name__ == '__lambda_function__':
    lambda_handler()
