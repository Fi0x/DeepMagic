package com.fi0x.deepmagic.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomTrigger implements ICriterionTrigger<CustomTrigger.Instance>
{
    private final ResourceLocation RL;
    private final Map<PlayerAdvancements, CustomTrigger.Listeners> listeners = Maps.newHashMap();

    public CustomTrigger(String parString)
    {
        super();
        RL = new ResourceLocation(parString);
    }

    public CustomTrigger(ResourceLocation parRL)
    {
        super();
        RL = parRL;
    }

    @Nonnull
    @Override
    public ResourceLocation getId()
    {
        return RL;
    }

    @Override
    public void addListener(@Nonnull PlayerAdvancements playerAdvancementsIn, @Nonnull ICriterionTrigger.Listener listener)
    {
        CustomTrigger.Listeners customTriggerListeners = listeners.get(playerAdvancementsIn);

        if (customTriggerListeners == null)
        {
            customTriggerListeners = new CustomTrigger.Listeners(playerAdvancementsIn);
            listeners.put(playerAdvancementsIn, customTriggerListeners);
        }

        customTriggerListeners.add(listener);
    }

    @Override
    public void removeListener(@Nonnull PlayerAdvancements playerAdvancementsIn, @Nonnull ICriterionTrigger.Listener listener)
    {
        CustomTrigger.Listeners customTriggerListeners = listeners.get(playerAdvancementsIn);

        if (customTriggerListeners != null)
        {
            customTriggerListeners.remove(listener);

            if (customTriggerListeners.isEmpty())
                listeners.remove(playerAdvancementsIn);
        }
    }

    @Override
    public void removeAllListeners(@Nonnull PlayerAdvancements playerAdvancementsIn)
    {
        listeners.remove(playerAdvancementsIn);
    }

    @Nonnull
    @Override
    public CustomTrigger.Instance deserializeInstance(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context)
    {
        return new CustomTrigger.Instance(getId());
    }

    public void trigger(EntityPlayerMP parPlayer)
    {
        CustomTrigger.Listeners customTriggerListeners = listeners.get(parPlayer.getAdvancements());

        if (customTriggerListeners != null)
            customTriggerListeners.trigger(parPlayer);
    }

    public static class Instance extends AbstractCriterionInstance
    {

        public Instance(ResourceLocation parRL)
        {
            super(parRL);
        }

        public boolean test()
        {
            return true;
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<CustomTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty()
        {
            return listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<CustomTrigger.Instance> listener)
        {
            listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<CustomTrigger.Instance> listener)
        {
            listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player)
        {
            List<Listener<CustomTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<CustomTrigger.Instance> listener : listeners)
            {
                if (listener.getCriterionInstance().test())
                {
                    if (list == null)
                        list = Lists.newArrayList();

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (ICriterionTrigger.Listener<CustomTrigger.Instance> listener1 : list)
                    listener1.grantCriterion(playerAdvancements);
            }
        }
    }
}
