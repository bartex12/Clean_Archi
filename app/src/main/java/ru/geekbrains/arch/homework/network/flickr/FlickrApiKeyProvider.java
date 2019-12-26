package ru.geekbrains.arch.homework.network.flickr;

import ru.geekbrains.arch.homework.R;
import ru.geekbrains.arch.homework.network.ApiKeyProvider;
import ru.geekbrains.arch.homework.util.resources.ResourceManager;

public class FlickrApiKeyProvider implements ApiKeyProvider {

    private final ResourceManager resourceManager;

    public FlickrApiKeyProvider(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public String getApiKey() {
        // FIXME: you should provide your own Flickr API key here
        // see https://www.flickr.com/services/apps/create/apply/
        return resourceManager.getString(R.string.flick_api_key);
    }
}
