package main.client.aspects;

public aspect TestAspect {

    pointcut demoPointcut() : call(* *.methodA());

    before() : demoPointcut() {
        System.out.println("Before: " + thisJoinPoint);
    }

    after() : demoPointcut() {
        System.out.println("After: " + thisJoinPoint);
    }


}
