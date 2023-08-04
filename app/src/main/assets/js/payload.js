// noinspection JSUnresolvedReference,JSIgnoredPromiseFromCall

// Load in modules
const blacklist = (id) => Object.defineProperty(window.modules, id, {
    value: window.modules[id],
    enumerable: false,
    configurable: true,
    writable: true
});

for (const key in window.modules) {
    const id = Number(key);
    const module = window.modules[id]?.publicModule?.exports;

    if (!module || module === window || module["proxygone"] === null) {
        blacklist(id);
    }
}

const originalHandler = window.ErrorUtils.getGlobalHandler();

const filterModules = (modules, single = false) => (filter) => {
    const found = [];

    for (const key in modules) {
        const id = Number(key);
        const module = modules[id]?.publicModule?.exports;

        if (!modules[id].isInitialized) try {
            window.ErrorUtils.setGlobalHandler(() => {});
            __r(id);
            window.ErrorUtils.setGlobalHandler(originalHandler);
        } catch {}

        if (!module) {
            blacklist(id);
            continue;
        }

        if (module.default && module.__esModule && filter(module.default)) {
            if (single) return module.default;
            found.push(module.default);
        }

        if (filter(module)) {
            if (single) return module;
            else found.push(module);
        }
    }

    if (!single) return found;
}

const find = filterModules(window.modules, true);

const propsFilter = (props) => (m) => props.every((p) => m[p] !== undefined);
const storeFilter = (name) => (m) => m.getName && m.getName.length === 0 && m.getName() === name;

const findByProps = (...props) => find(propsFilter(props));
const findByStoreName = (name) => find(storeFilter(name));

const FluxDispatcher = findByProps("_currentDispatchActionType");
const moment = findByProps("isMoment")
const ThemeManager = findByProps("updateTheme", "overrideTheme");
const AMOLEDThemeManager = findByProps("setAMOLEDThemeEnabled");
const ThemeStore = findByStoreName("ThemeStore");
const UnsyncedUserSettingsStore = findByStoreName("UnsyncedUserSettingsStore");

// Fix Themes & Timestamps

function onDispatch({ locale }) {
    try {
        ThemeManager.overrideTheme(ThemeStore?.theme ?? "dark");
        if (AMOLEDThemeManager && UnsyncedUserSettingsStore.useAMOLEDTheme === 2) AMOLEDThemeManager.setAMOLEDThemeEnabled(true);
    } catch(e) {
        console.error("Failed to fix theme...", e);
    }

    try {
        moment.locale(locale.toLowerCase());
    } catch(e) {
        console.error("Failed to fix timestamps...", e);
    }

    FluxDispatcher.unsubscribe("I18N_LOAD_SUCCESS", onDispatch);
}

FluxDispatcher.subscribe("I18N_LOAD_SUCCESS", onDispatch);

const { getSerializedState } = findByProps("getSerializedState");
const { getCurrentUser } = findByStoreName("UserStore");

// Enable Experiments

function enable() {
    try {
        const user = getCurrentUser();

        user.flags += 1;

        const actionHandlers = FluxDispatcher._actionHandlers._computeOrderedActionHandlers("OVERLAY_INITIALIZE").filter(e => e.name.includes("Experiment"));

        actionHandlers.forEach(({ actionHandler }) => actionHandler({
            serializedExperimentStore: getSerializedState(),
            user,
        }));
    } catch(e) {
        console.error(`Failed to enable experiments...`, e);
    }
}

function payload() {
    FluxDispatcher.unsubscribe("CONNECTION_OPEN", payload);
    enable();
}

getCurrentUser() ? enable() : FluxDispatcher.subscribe("CONNECTION_OPEN", payload);