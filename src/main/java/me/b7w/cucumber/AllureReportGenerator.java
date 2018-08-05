package me.b7w.cucumber;

import io.qameta.allure.ConfigurationBuilder;
import io.qameta.allure.DummyReportGenerator;
import io.qameta.allure.Extension;
import io.qameta.allure.ReportGenerator;
import io.qameta.allure.allure1.Allure1Plugin;
import io.qameta.allure.allure2.Allure2Plugin;
import io.qameta.allure.category.CategoriesPlugin;
import io.qameta.allure.category.CategoriesTrendPlugin;
import io.qameta.allure.context.FreemarkerContext;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.context.MarkdownContext;
import io.qameta.allure.context.RandomUidContext;
import io.qameta.allure.core.*;
import io.qameta.allure.duration.DurationPlugin;
import io.qameta.allure.duration.DurationTrendPlugin;
import io.qameta.allure.environment.Allure1EnvironmentPlugin;
import io.qameta.allure.executor.ExecutorPlugin;
import io.qameta.allure.history.HistoryPlugin;
import io.qameta.allure.history.HistoryTrendPlugin;
import io.qameta.allure.influxdb.InfluxDbExportPlugin;
import io.qameta.allure.launch.LaunchPlugin;
import io.qameta.allure.mail.MailPlugin;
import io.qameta.allure.owner.OwnerPlugin;
import io.qameta.allure.plugin.DefaultPluginLoader;
import io.qameta.allure.prometheus.PrometheusExportPlugin;
import io.qameta.allure.retry.RetryPlugin;
import io.qameta.allure.retry.RetryTrendPlugin;
import io.qameta.allure.severity.SeverityPlugin;
import io.qameta.allure.status.StatusChartPlugin;
import io.qameta.allure.suites.SuitesPlugin;
import io.qameta.allure.summary.SummaryPlugin;
import io.qameta.allure.tags.TagsPlugin;
import io.qameta.allure.timeline.TimelinePlugin;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AllureReportGenerator {

    private static final List<Extension> EXTENSIONS = Arrays.asList(
            new JacksonContext(),
            new MarkdownContext(),
            new FreemarkerContext(),
            new RandomUidContext(),
            new MarkdownDescriptionsPlugin(),
            new RetryPlugin(),
            new RetryTrendPlugin(),
            new TagsPlugin(),
            new SeverityPlugin(),
            new OwnerPlugin(),
            new CategoriesPlugin(),
            new CategoriesTrendPlugin(),
            new HistoryPlugin(),
            new HistoryTrendPlugin(),
            new DurationPlugin(),
            new DurationTrendPlugin(),
            new StatusChartPlugin(),
            new TimelinePlugin(),
            new SuitesPlugin(),
            new TestsResultsPlugin(),
            new AttachmentsPlugin(),
            new MailPlugin(),
            new InfluxDbExportPlugin(),
            new PrometheusExportPlugin(),
            new SummaryPlugin(),
            new ExecutorPlugin(),
            new LaunchPlugin(),
            new Allure1Plugin(),
            new Allure1EnvironmentPlugin(),
            new Allure2Plugin(),
            new ReportWebPlugin()
    );

    private String resultFolder;

    public AllureReportGenerator(String resultFolder) {
        this.resultFolder = resultFolder;
    }

    public void writeTo(String path) {
        File out = new File(path);
        if (out.exists()) {
            FileSystemUtils.deleteRecursively(out);
        }
        out.mkdir();
        ReportGenerator generator = generator();
        try {
            generator.generate(out.toPath(), Paths.get(resultFolder));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ReportGenerator generator() {
        final List<Plugin> plugins = loadPlugins();
        final Configuration configuration = new ConfigurationBuilder()
                .fromExtensions(EXTENSIONS)
                .fromPlugins(plugins)
                .build();
        return new ReportGenerator(configuration);
    }

    private static List<Plugin> loadPlugins() {
        final Optional<Path> optional = Optional.ofNullable(System.getProperty("allure.plugins.directory"))
                .map(Paths::get)
                .filter(Files::isDirectory);
        if (!optional.isPresent()) {
            return Collections.emptyList();
        }
        final Path pluginsDirectory = optional.get();
        final DefaultPluginLoader loader = new DefaultPluginLoader();
        final ClassLoader classLoader = DummyReportGenerator.class.getClassLoader();
        return listFiles(pluginsDirectory)
                .filter(Files::isDirectory)
                .map(pluginDir -> loader.loadPlugin(classLoader, pluginDir))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Stream<Path> listFiles(Path pluginsDirectory) {
        try {
            return Files.list(pluginsDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
