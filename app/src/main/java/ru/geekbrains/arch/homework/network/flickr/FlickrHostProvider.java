package ru.geekbrains.arch.homework.network.flickr;

import ru.geekbrains.arch.homework.R;
import ru.geekbrains.arch.homework.network.HostProvider;
import ru.geekbrains.arch.homework.util.resources.ResourceManager;

public class FlickrHostProvider implements HostProvider {
    private final ResourceManager resourceManager;

    public FlickrHostProvider(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public String getHostUrl() {
        //
        return resourceManager.getString(R.string.flick_host);
    }
}
