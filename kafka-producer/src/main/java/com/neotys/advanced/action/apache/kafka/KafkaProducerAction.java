package com.neotys.advanced.action.apache.kafka;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.google.common.base.Optional;
import com.neotys.action.argument.Arguments;
import com.neotys.action.argument.Option;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public final class KafkaProducerAction implements Action {
    private static final String BUNDLE_NAME = "com.neotys.advanced.action.apache.kafka.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");
    private static final ImageIcon DISPLAY_ICON;

    static {
        URL iconURL = KafkaProducerAction.class.getResource(ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("iconPath"));
        if (iconURL != null) {
            DISPLAY_ICON = new ImageIcon(iconURL);
        } else {
            DISPLAY_ICON = null;
        }
    }

    @Override
    public String getType() {
        return "KafkaProducer";
    }

    @Override
    public List<ActionParameter> getDefaultActionParameters() {
        final ArrayList<ActionParameter> parameters = new ArrayList<>();

        // Build the parameter list
        KafkaProducerOption[] arrayOfConnectOption;
        int j = (arrayOfConnectOption = KafkaProducerOption.values()).length;
        for (int i = 0; i < j; i++) {
			KafkaProducerOption option = arrayOfConnectOption[i];
			if (Option.AppearsByDefault.True.equals(option.getAppearsByDefault())){
			    parameters.add(new ActionParameter(option.getName(),option.getDefaultValue(),option.getType()));
            }
        }

        return parameters;
    }

    @Override
    public Class<? extends ActionEngine> getEngineClass() {
        return KafkaProducerActionEngine.class;
    }

    @Override
    public Icon getIcon() {
        return DISPLAY_ICON;
    }

    @Override
    public boolean getDefaultIsHit() {
        return false;
    }

    @Override
    public String getDescription() {

        // Build the description of the advanced action
        return ("Send Kafka Message to a topic.\n" +
                "More information can be found here : http://kafka.apache.org/0102/documentation/#producerconfigs\n\n" +
                Arguments.getArgumentDescriptions(KafkaProducerOption.values()) + "\n" +
                "Other possible parameters : ssl.key.password, ssl.keystore.location, ssl.keystore.password, ssl.truststore.location, ssl.truststore.password, connections.max.idle.ms, linger.ms, max.block.ms, max.request.size, partitioner.class, receive.buffer.bytes, request.timeout.ms, sasl.jaas.config, sasl.kerberos.service.name, sasl.mechanism, security.protocol, send.buffer.bytes, ssl.enabled.protocols, ssl.keystore.type, ssl.protocol, ssl.provider, ssl.truststore.type, timeout.ms, block.on.buffer.full, interceptor.classes, max.in.flight.requests.per.connection, metadata.fetch.timeout.ms, metadata.max.age.ms, metric.reporters, metrics.num.samples, metrics.sample.window.ms, reconnect.backoff.ms, retry.backoff.ms, sasl.kerberos.kinit.cmd, sasl.kerberos.min.time.before.relogin, sasl.kerberos.ticket.renew.jitter, sasl.kerberos.ticket.renew.window.factor, ssl.cipher.suites, ssl.endpoint.identification.algorithm, ssl.keymanager.algorithm, ssl.secure.random.implementation, ssl.trustmanager.algorithm");
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public String getDisplayPath() {
        return DISPLAY_PATH;
    }

    @Override
    public Optional<String> getMinimumNeoLoadVersion() {
        return Optional.absent();
    }

    @Override
    public Optional<String> getMaximumNeoLoadVersion() {
        return Optional.absent();
    }


}
