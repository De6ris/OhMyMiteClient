package com.github.Debris.ommc.util;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.Item;

import java.util.Collection;

public class StringUtil {
//    public static List<String> getConfigOptionListHoverString(IConfigOptionList config) {
//        IConfigOptionListEntry defaultEntry = config.getDefaultOptionListValue();
//        ImmutableList.Builder<IConfigOptionListEntry> builder = ImmutableList.builder();
//        builder.add(defaultEntry);
//        IConfigOptionListEntry next = defaultEntry.cycle(true);
//        while (next != defaultEntry) {
//            builder.add(next);
//            next = next.cycle(true);
//        }
//        ImmutableList<IConfigOptionListEntry> entries = builder.build();
//
//        IConfigOptionListEntry currentEntry = config.getOptionListValue();
//        List<String> hover = new ArrayList<>();
//        hover.add("可用值:");
//        for (IConfigOptionListEntry entry : entries) {
//            if (entry == defaultEntry) {
//                hover.add(GuiBase.TXT_AQUA + entry.getDisplayName() + "<--默认值");
//            } else if (entry == currentEntry) {
//                hover.add(GuiBase.TXT_GREEN + entry.getDisplayName() + "<--当前值");
//            } else {
//                hover.add(entry.getDisplayName());
//            }
//        }
//        return hover;
//    }

//    public static boolean isModLoadedWithNewEnoughVersion(String modId, String leastVersion) {
//        Optional<ModContainer> optional = FabricLoader.getInstance().getModContainer(modId);
//        if (optional.isEmpty()) return false;
//        Version version = optional.get().getMetadata().getVersion();
//        try {
//            Version parse = Version.parse(leastVersion);
//            if (version.compareTo(parse) >= 0) return true;
//        } catch (VersionParsingException e) {
//            return false;
//        }
//        return false;
//    }

    public static String translateItem(Item item) {
        return StringUtils.translate(item.getUnlocalizedName());
    }

    public static String translateItemCollection(Collection<Item> items) {
        return items.stream().map(StringUtil::translateItem).toList().toString();
    }
}
