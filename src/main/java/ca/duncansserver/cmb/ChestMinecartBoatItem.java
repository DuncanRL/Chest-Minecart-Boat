package ca.duncansserver.cmb;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class ChestMinecartBoatItem extends Item {
    private static final Predicate<Entity> RIDERS;
    private final BoatEntity.Type type;

    public ChestMinecartBoatItem(BoatEntity.Type type, Item.Settings settings) {
        super(settings);
        this.type = type;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = rayTrace(world, user, RayTraceContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            List<Entity> list = world.getEntities((Entity) user,
                    user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), RIDERS);
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getCameraPosVec(1.0F);
                Iterator<Entity> var11 = list.iterator();

            while(var11.hasNext()) {
               Entity entity = (Entity)var11.next();
               Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
               if (box.contains(vec3d2)) {
                  return TypedActionResult.pass(itemStack);
               }
            }
         }

         if (hitResult.getType() == HitResult.Type.BLOCK) {
            BoatEntity boatEntity = new BoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
            boatEntity.setBoatType(this.type);
            boatEntity.yaw = user.yaw;

            

            //boatEntity.getPassengerList().add(new ChestMinecartEntity(world,0.0,0.0,0.0));
            if (!world.doesNotCollide(boatEntity, boatEntity.getBoundingBox().expand(-0.1D))) {
               return TypedActionResult.fail(itemStack);
            } else {
               if (!world.isClient) {
                  ChestMinecartEntity cmEntity = new ChestMinecartEntity(world, boatEntity.getX(),boatEntity.getY(),boatEntity.getZ());
                  cmEntity.yaw = user.yaw;
                  world.spawnEntity(cmEntity);
                  world.spawnEntity(boatEntity);
                  cmEntity.startRiding(boatEntity);

                  if (!user.abilities.creativeMode) {
                     itemStack.decrement(1);
                  }
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               return TypedActionResult.method_29237(itemStack, world.isClient());
            }
         } else {
            return TypedActionResult.pass(itemStack);
         }
      }
   }

   static {
      RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
   }
}
