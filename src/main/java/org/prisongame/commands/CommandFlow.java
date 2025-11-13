package org.prisongame.commands;

import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class CommandFlow extends SubmissionPublisher<Command> {

    ArrayList<CommandSubscriber> inputs;

    public CommandFlow () {
        inputs = new ArrayList<CommandSubscriber>();
    }

    public void addFlowIn(SubmissionPublisher<String> flowIn) {
        inputs.add(new CommandSubscriber(flowIn));
    }
}

class CommandSubscriber implements Flow.Subscriber<String> {
    Flow.Subscription subscription;
    Parser parser;
    SubmissionPublisher<Command> flowOut;
    SubmissionPublisher<String> flowIn;

    public CommandSubscriber( SubmissionPublisher<String> flowIn) {
        this.flowIn = flowIn;
        flowIn.subscribe(this);
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        flowOut.submit(parser.parseCommand(item));
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    } //TODO: Add Error Protection

    @Override
    public void onComplete() {

    }
}
