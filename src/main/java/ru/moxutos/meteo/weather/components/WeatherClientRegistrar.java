package ru.moxutos.meteo.weather.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.moxutos.meteo.weather.clients.WeatherClient;
import ru.moxutos.meteo.weather.mapper.WeatherSourceMapper;
import ru.moxutos.meteo.weather.model.record.WeatherSource;
import ru.moxutos.meteo.weather.repository.read.WeatherSourceReadRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherClientRegistrar implements SmartInitializingSingleton {

    private final WeatherSourceReadRepository weatherSourceReadRepository;
    private final WeatherClientFactory weatherClientFactory;
    private final WeatherSourceMapper weatherSourceMapper;
    private final ConfigurableApplicationContext applicationContext;

    @Override
    public void afterSingletonsInstantiated() {

        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) applicationContext.getBeanFactory();

        weatherSourceReadRepository.findAllEnabled()
                .forEach(source -> {
                    log.debug("Создаем бин клиента для: {}", source.getName());
                    WeatherSource weatherSource = weatherSourceMapper.toRecord(source);
                    WeatherClient client = weatherClientFactory.create(weatherSource);

                    String beanName = "weatherClient_" + source.getName();

                    beanFactory.registerSingleton(beanName, client);
                    log.info("Зарегистрировали бин: {}", beanName);
                });
    }
}
