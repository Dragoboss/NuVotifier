package com.vexsoftware.votifier;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import io.netty.channel.Channel;

public interface VoteHandler {
    void onVoteReceived(Vote vote, VotifierSession.ProtocolVersion protocolVersion, String remoteAddress) throws Exception;

    // these default members are kept for compatibility sake - there probably isn't anyone out there implementing these
    // on their own, but we still deprecate them before removing them because we are nice!
    default void onError(Channel channel, boolean voteAlreadyCompleted, Throwable throwable) {
        onError(channel, throwable);
    }

    @Deprecated
    default void onError(Channel channel, Throwable throwable) {
        throw new RuntimeException("Unimplemented onError handler");
    }
}
