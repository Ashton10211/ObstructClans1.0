package com.obstruct.clans.map.nms;

import org.bukkit.block.Block;

public interface INMSHandler {
    MaterialMapColorInterface getColorNeutral();

    MaterialMapColorInterface getBlockColor(Block paramBlock);
}
