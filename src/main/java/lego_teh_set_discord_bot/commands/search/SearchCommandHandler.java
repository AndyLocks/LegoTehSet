package lego_teh_set_discord_bot.commands.search;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rebrickableAPI.OrderingType;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;
import spring_config.SpringConfig;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * Обработчик команды search
 * <p>
 * Тут спрятана вся логика команды search.
 * Этим классом пользуется {@link CommandSearch} для получения {@link CommandSearchResponse}
 */
public class SearchCommandHandler {
    private static final Logger LOGGER = Logger.getLogger(SearchCommandHandler.class.getName());
    private final ConcurrentMap<String, SetsContainer> setsContainerMap = new ConcurrentHashMap<>();
    private final Button firstButton = Button.secondary("search_first", Emoji.fromFormatted("<:rewind_lts:1156918918103965716>"));
    private final Button lastButton = Button.secondary("search_last", Emoji.fromFormatted("<:fast_forward_lts:1156830249250725948>"));
    private final Button arrowBackwardButton = Button.secondary("search_arrow_backward", Emoji.fromFormatted("<:arrow_backward_lts:1156919033497657345>"));
    private final Button arrowForwardButton = Button.secondary("search_arrow_forward", Emoji.fromFormatted("<:arrow_forward_lts:1156918988572475455>"));
    private final Button pageButton = Button.secondary("search_text_input_button", "page");

    /**
     * Получить актуальные кнопки.
     * <p>
     * Актуальность кнопок зависит от текущего индекса.
     *
     * @param setsContainer контейнер с наборами
     * @return возвращает список кнопок в зависимости от текущего индекса
     *
     * @see SetsContainer
     */
    private List<Button> getButtonList(SetsContainer setsContainer) {
        List<Button> buttonList = new ArrayList<>();
        if(setsContainer.hasPrev()) {
            buttonList.add(firstButton);
            buttonList.add(arrowBackwardButton);
        }
        else {
            buttonList.add(firstButton.asDisabled());
            buttonList.add(arrowBackwardButton.asDisabled());
        }
        if(setsContainer.hasNext()) {
            buttonList.add(arrowForwardButton);
            buttonList.add(lastButton);
        }
        else {
            buttonList.add(arrowForwardButton.asDisabled());
            buttonList.add(lastButton.asDisabled());
        }
        buttonList.add(pageButton);

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return buttonList;
    }

    public SearchCommandHandler() {
        LOGGER.setLevel(Level.FINE);
        try{
            FileHandler fileHandler = new FileHandler("/home/illia/IdeaProjects/LegoTehSet/search.log");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Инициализация всего необходимого для работы команды search
     * <p>
     * Подготавливает все к работе и добавляет {@link SetsContainer} в {@link #setsContainerMap}
     *
     * @param search поисковой запрос от пользователя
     * @param orderingType тема, по которой нужно сортировать наборы. Может также быть и null
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ
     */
    public CommandSearchResponse init(String search, OrderingType orderingType, String interactionId) {
        List<Set> resultSetList;
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
            if(orderingType == null)
                resultSetList = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getSearchResult(search);
            else
                resultSetList = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getSearchResult(search, orderingType);
        }

        SetsContainer setsContainer = new SetsContainer(resultSetList);

        this.setsContainerMap.put(interactionId, setsContainer);

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                setsContainer.getCurrentSet(),
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Взаимодействие с кнопкой вперед
     * <p>
     * Изменяет {@link SetsContainer} в {@link #setsContainerMap}
     * При этом перемещает индекс на один элемент вперед
     *
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ уже с перемещенным индексом
     * @throws EmptySetsContainerException если контейнер оказался пустым
     */
    public CommandSearchResponse arrowForward(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.next();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Взаимодействие с кнопкой назад
     * <p>
     * Изменяет {@link SetsContainer} в {@link #setsContainerMap}
     * При этом перемещает индекс на один элемент назад
     *
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ уже с перемещенным индексом
     * @throws EmptySetsContainerException если контейнер оказался пустым
     */
    public CommandSearchResponse arrowBackward(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.prev();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Перейти к самому началу
     * <p>
     * Изменяет {@link SetsContainer} в {@link #setsContainerMap}
     * При этом перемещает индекс в самое начало
     *
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ уже с перемещенным индексом
     * @throws EmptySetsContainerException если контейнер оказался пустым
     */
    public CommandSearchResponse toStart(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toStart();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Перейти в самый конец
     * <p>
     * Изменяет {@link SetsContainer} в {@link #setsContainerMap}
     * При этом перемещает индекс в самый конец
     *
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ уже с перемещенным индексом
     * @throws EmptySetsContainerException если контейнер оказался пустым
     */
    public CommandSearchResponse toEnd(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toEnd();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Взаимодействие с кнопкой страницы.
     * Кнопка страницы позволяет пользователю выбрать нужную страницу.
     * <p>
     * Изменяет {@link SetsContainer} в {@link #setsContainerMap}
     * При этом перемещает индекс в зависимости от страницы
     *
     * @param interactionId уникальное айди взаимодействия для взаимодействия с discord. Также является ключем для {@link #setsContainerMap}
     * @return возвращает ответ уже с перемещенным индексом
     * @throws EmptySetsContainerException если контейнер оказался пустым
     * @throws IndexOutOfBoundsException если индекс выходит за рамки дозволенного
     */
    public CommandSearchResponse pageButtonInteraction(String interactionId, int page) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.setCurrentIndex(page);
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }
}
