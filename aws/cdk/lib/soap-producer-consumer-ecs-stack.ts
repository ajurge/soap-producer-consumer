import * as cdk from 'aws-cdk-lib';
import {Construct} from 'constructs';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import * as ecsp from 'aws-cdk-lib/aws-ecs-patterns';
import {ApplicationProtocol, Protocol as AlbProtocol} from 'aws-cdk-lib/aws-elasticloadbalancingv2';

export class SoapProducerConsumerEcsStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const soapProducerService = new ecsp.ApplicationMultipleTargetGroupsFargateService(this, 'SoapProducerService', {
      cpu: 1024,
      memoryLimitMiB: 4096,
      desiredCount: 1,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry('ajurge/soap-producer-consumer:soap-producer_1.0.0_master_0bc644a_20250110_075433'),
        containerPorts: [
          8081,
        ]
      },
      loadBalancers: [
        {
          name: 'soap-producer-alb',
          publicLoadBalancer: true,
          listeners: [
            {
              name: 'soap-producer-listener',
              port: 8081,
              protocol: ApplicationProtocol.HTTP
            },
          ],
        },
      ],
      targetGroups: [
        {
          listener: 'soap-producer-listener',
          containerPort: 8081,
          protocol: ecs.Protocol.TCP,
        },
      ],
    });

    soapProducerService.targetGroups[0].configureHealthCheck({
      path: '/actuator/health',
      interval: cdk.Duration.seconds(10),
      unhealthyThresholdCount: 5,
      port: String(8081),
      protocol: AlbProtocol.HTTP,
    });

    const soapConsumerService = new ecsp.ApplicationMultipleTargetGroupsFargateService(this, 'SoapConsumerService', {
      cpu: 1024,
      memoryLimitMiB: 4096,
      desiredCount: 1,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry('ajurge/soap-producer-consumer:soap-consumer_1.0.0_master_0bc644a_20250110_075433'),
        environment: {
          SOAP_PRODUCER_URL: soapProducerService.loadBalancers[0].loadBalancerDnsName.replace('http://', ''),
        },
        containerPorts: [
          8080,
        ]
      },
      loadBalancers: [
        {
          name: 'soap-consumer-alb',
          publicLoadBalancer: true,
          listeners: [
            {
              name: 'soap-consumer-listener',
              port: 8080,
              protocol: ApplicationProtocol.HTTP
            },
          ],
        },
      ],
      targetGroups: [
        {
          listener: 'soap-consumer-listener',
          containerPort: 8080,
          protocol: ecs.Protocol.TCP,
        },
      ],
    });

    soapConsumerService.targetGroups[0].configureHealthCheck({
      path: '/actuator/health',
      interval: cdk.Duration.seconds(10),
      unhealthyThresholdCount: 5,
      port: String(8080),
      protocol: AlbProtocol.HTTP,
    });
  }
}
