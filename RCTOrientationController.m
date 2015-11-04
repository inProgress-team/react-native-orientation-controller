//
//  RCTOrientationListener.m
//
//  Created by Julien Sierra on 29/10/15.
//

#import "RCTOrientationController.h"
#import "RCTBridge.h"

@implementation RCTOrientationController

@synthesize bridge = _bridge;

- (instancetype)init
{
    if ((self = [super init])) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(deviceOrientationDidChange:) name:UIDeviceOrientationDidChangeNotification object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(applicationOrientationDidChange:) name:UIApplicationDidChangeStatusBarOrientationNotification object:nil];

    }
    return self;
    
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)deviceOrientationDidChange:(NSNotification *)notification
{
    int orientation = [self getDeviceOrientationAsNumber];
    NSString *orientationStr;
    switch (orientation) {
        case 0:
            orientationStr = @"PORTRAIT";
            break;
        case 1:
            orientationStr = @"LANDSCAPE LEFT";
            break;
        case 2:
            orientationStr = @"PORTRAIT UPSIDE DOWN";
            break;
        case 3:
            orientationStr = @"LANDSCAPE RIGHT";
            break;
        default:
            orientationStr = @"UNKNOWN";
            break;
    }
    
    UIDevice *currentDevice = [UIDevice currentDevice];
    NSString *deviceStr = [currentDevice model];
    
    [_bridge.eventDispatcher sendDeviceEventWithName:@"deviceOrientationDidChange"
                                                body:@{@"orientation": orientationStr,@"device": deviceStr}];
}

- (void)applicationOrientationDidChange:(NSNotification *)notification
{
    int orientation = [self getApplicationOrientationAsNumber];
    NSString *orientationStr;
    switch (orientation) {
        case 0:
            orientationStr = @"PORTRAIT";
            break;
        case 1:
            orientationStr = @"LANDSCAPE LEFT";
            break;
        case 2:
            orientationStr = @"PORTRAIT UPSIDE DOWN";
            break;
        case 3:
            orientationStr = @"LANDSCAPE RIGHT";
            break;
        default:
            orientationStr = @"UNKNOWN";
            break;
    }
    
    UIDevice *currentDevice = [UIDevice currentDevice];
    NSString *deviceStr = [currentDevice model];
    
    [_bridge.eventDispatcher sendDeviceEventWithName:@"applicationOrientationDidChange"
                                                body:@{@"orientation": orientationStr,@"device": deviceStr}];
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getDeviceOrientation:(RCTResponseSenderBlock)callback)
{
    
    int orientation = [self getDeviceOrientationAsNumber];
    NSString *orientationStr;
    switch (orientation) {
        case 0:
            orientationStr = @"PORTRAIT";
            break;
        case 1:
            orientationStr = @"LANDSCAPE LEFT";
            break;
        case 2:
            orientationStr = @"PORTRAIT UPSIDE DOWN";
            break;
        case 3:
            orientationStr = @"LANDSCAPE RIGHT";
            break;
        default:
            orientationStr = @"UNKNOWN";
            break;
    }
    
    UIDevice *currentDevice = [UIDevice currentDevice];
    NSString *deviceStr = [currentDevice model];
    
    NSArray *orientationArray = @[orientationStr, deviceStr];
    callback(orientationArray);
}

RCT_EXPORT_METHOD(getApplicationOrientation:(RCTResponseSenderBlock)callback)
{
    
    int orientation = [self getApplicationOrientationAsNumber];
    NSString *orientationStr;
    switch (orientation) {
        case 0:
            orientationStr = @"PORTRAIT";
            break;
        case 1:
            orientationStr = @"LANDSCAPE LEFT";
            break;
        case 2:
            orientationStr = @"PORTRAIT UPSIDE DOWN";
            break;
        case 3:
            orientationStr = @"LANDSCAPE RIGHT";
            break;
        default:
            orientationStr = @"UNKNOWN";
            break;
    }
    
    UIDevice *currentDevice = [UIDevice currentDevice];
    NSString *deviceStr = [currentDevice model];
    
    NSArray *orientationArray = @[orientationStr, deviceStr];
    callback(orientationArray);
}


-(int)getDeviceOrientationAsNumber{
    UIDevice *currentDevice = [UIDevice currentDevice];
    UIDeviceOrientation orientation = [currentDevice orientation];
    
    switch (orientation) {
        case UIDeviceOrientationPortrait:
            return 0;
            break;
        case UIDeviceOrientationPortraitUpsideDown:
            return 2;
            break;
        case UIDeviceOrientationLandscapeLeft:
            return 1;
            break;
        case UIDeviceOrientationLandscapeRight:
            return 3;
            break;
        default:
            return 0;
            break;
    }
    
}

-(int)getApplicationOrientationAsNumber{
    UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
    
    switch (orientation) {
        case UIInterfaceOrientationPortrait:
            return 0;
            break;
        case UIInterfaceOrientationLandscapeLeft:
            return 3;
            break;
        case UIInterfaceOrientationPortraitUpsideDown:
            return 2;
            break;
        case UIInterfaceOrientationLandscapeRight:
            return 1;
            break;
        default:
            return 0;
            break;
    }

}

RCT_EXPORT_METHOD(rotate:(int)rotation)
{
    if(rotation>3||rotation<1)rotation = 0;
    NSNumber *value;
    int orientation = ([self getApplicationOrientationAsNumber] + rotation)%4;

    switch (orientation) {
        case 1:
            value = [NSNumber numberWithInt:UIInterfaceOrientationLandscapeRight];
            break;
        case 2:
            value = [NSNumber numberWithInt:UIInterfaceOrientationPortraitUpsideDown];
            break;
        case 3:
            value = [NSNumber numberWithInt:UIInterfaceOrientationLandscapeLeft];
            break;
        case 0:
            value = [NSNumber numberWithInt:UIInterfaceOrientationPortrait];
            break;
            
        default:
            break;
    }
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [[UIDevice currentDevice] setValue:value forKey:@"orientation"];
        [UIViewController attemptRotationToDeviceOrientation];
        
    });
}

@end
