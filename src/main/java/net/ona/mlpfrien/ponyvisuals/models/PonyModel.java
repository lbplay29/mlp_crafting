package net.ona.mlpfrien.ponyvisuals.models;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.ponyvisuals.animation.PonyAnimationLogic;
import net.ona.mlpfrien.ponyvisuals.animation.PonyAnimationController;
import net.ona.mlpfrien.ponyvisuals.animation.PonyAnimationManager;
import org.joml.Vector3f;


public class PonyModel<T extends AbstractClientPlayer> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "pony"), "main");
	public final ModelPart root;
	public final ModelPart head;
	public final ModelPart Horn;
	public final ModelPart HornOverlay;
	public final ModelPart hairs;
	public final ModelPart hair_0;
	public final ModelPart hair_1;
	public final ModelPart hair_2;
	public final ModelPart hair_3;
	public final ModelPart hair_4;
	public final ModelPart hair_5;
	public final ModelPart heldItem;
	public final ModelPart body;
	public final ModelPart tail;
	public final ModelPart wings;
	public final ModelPart RWing;
	public final ModelPart lowerWingR;
	public final ModelPart LWing;
	public final ModelPart lowerWingR2;
	public final ModelPart Arms;
	public final ModelPart rightArm;
	public final ModelPart rightSleeve;
	public final ModelPart leftArm;
	public final ModelPart leftSleeve;
	public final ModelPart Legs;
	public final ModelPart rightLeg;
	public final ModelPart leftLeg;
	public final ModelPart heldItemU;

	public PonyModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.Horn = this.head.getChild("Horn");
		this.HornOverlay = this.Horn.getChild("HornOverlay");
		this.hairs = this.head.getChild("hairs");
		this.hair_0 = this.hairs.getChild("hair_0");
		this.hair_1 = this.hairs.getChild("hair_1");
		this.hair_2 = this.hairs.getChild("hair_2");
		this.hair_3 = this.hairs.getChild("hair_3");
		this.hair_4 = this.hairs.getChild("hair_4");
		this.hair_5 = this.hairs.getChild("hair_5");
		this.heldItem = this.head.getChild("heldItem");
		this.body = this.root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.wings = this.body.getChild("wings");
		this.RWing = this.wings.getChild("RWing");
		this.lowerWingR = this.RWing.getChild("lowerWingR");
		this.LWing = this.wings.getChild("LWing");
		this.lowerWingR2 = this.LWing.getChild("lowerWingR2");
		this.Arms = this.root.getChild("Arms");
		this.rightArm = this.Arms.getChild("rightArm");
		this.rightSleeve = this.rightArm.getChild("rightSleeve");
		this.leftArm = this.Arms.getChild("leftArm");
		this.leftSleeve = this.leftArm.getChild("leftSleeve");
		this.Legs = this.root.getChild("Legs");
		this.rightLeg = this.Legs.getChild("rightLeg");
		this.leftLeg = this.Legs.getChild("leftLeg");
		this.heldItemU = this.root.getChild("heldItemU");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, -1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 44).addBox(-4.0F, -8.9F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 44).addBox(-4.0F, -8.9F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.15F))
				.texOffs(104, 23).addBox(-2.0F, -3.8F, -7.9F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -3.2F, -4.0F));

		PartDefinition Neck_r1 = head.addOrReplaceChild("Neck_r1", CubeListBuilder.create().texOffs(80, 43).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.9F, -0.75F, 0.1745F, 0.0F, 0.0F));

		PartDefinition Ear2_r1 = head.addOrReplaceChild("Ear2_r1", CubeListBuilder.create().texOffs(96, 109).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -8.5F, -1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition Ear1_r1 = head.addOrReplaceChild("Ear1_r1", CubeListBuilder.create().texOffs(88, 109).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -8.5F, -1.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition Horn = head.addOrReplaceChild("Horn", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 7.0F));

		PartDefinition cube_r1 = Horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(68, 34).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(110, 2).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -26.0F, -12.25F, 0.5236F, 0.0F, 0.0F));

		PartDefinition HornOverlay = Horn.addOrReplaceChild("HornOverlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = HornOverlay.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 34).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(118, 2).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -12.25F, 0.5236F, 0.0F, 0.0F));

		PartDefinition hairs = head.addOrReplaceChild("hairs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hair_0 = hairs.addOrReplaceChild("hair_0", CubeListBuilder.create().texOffs(46, 28).addBox(-4.0F, -27.5F, -10.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 17.5F, 5.0F));

		PartDefinition cube_r3 = hair_0.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 109).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-2.5F, -25.867F, -11.0714F, 0.8727F, 0.1745F, 0.0F));

		PartDefinition cube_r4 = hair_0.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(94, 2).addBox(-2.0F, -0.4F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(4.5F, -19.25F, -5.05F, -2.7303F, 0.5672F, -1.1911F));

		PartDefinition cube_r5 = hair_0.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(94, 2).addBox(-2.0F, -0.4F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(4.75F, -21.25F, -4.05F, -2.3445F, 0.9131F, -0.9446F));

		PartDefinition cube_r6 = hair_0.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 85).addBox(-2.0F, -0.4F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -24.0F, -2.55F, -1.5708F, 0.0F, -0.0873F));

		PartDefinition cube_r7 = hair_0.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(104, 14).addBox(-1.0F, -0.4F, -0.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -18.25F, -3.3F, -0.1427F, 1.2311F, 1.4423F));

		PartDefinition cube_r8 = hair_0.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(104, 14).addBox(-1.0F, -0.4F, -0.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, -19.25F, -2.05F, -1.4777F, 0.2947F, -0.4921F));

		PartDefinition cube_r9 = hair_0.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(34, 93).addBox(-2.0F, -0.4F, -1.5F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -21.25F, -2.3F, -1.3963F, 0.0F, -0.2618F));

		PartDefinition cube_r10 = hair_0.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(80, 55).addBox(-2.0F, -1.0F, -7.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -26.0F, -0.75F, 0.0F, 0.0F, -0.0436F));

		PartDefinition cube_r11 = hair_0.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(100, 85).mirror().addBox(-1.0F, -1.0F, -2.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -26.0F, -7.75F, 0.0547F, 0.3349F, -0.5432F));

		PartDefinition cube_r12 = hair_0.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(26, 109).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(4.75F, -25.117F, -9.5714F, 1.2654F, -0.6981F, 0.0F));

		PartDefinition cube_r13 = hair_0.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(26, 109).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(-4.7821F, -25.117F, -9.607F, 1.2217F, 0.6981F, 0.0F));

		PartDefinition cube_r14 = hair_0.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(100, 85).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -26.0F, -8.0F, 0.0547F, -0.3349F, 0.5432F));

		PartDefinition cube_r15 = hair_0.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(26, 109).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.5F, -25.617F, -11.0714F, 0.8727F, -0.1745F, 0.0F));

		PartDefinition hair_1 = hairs.addOrReplaceChild("hair_1", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition cube_r16 = hair_1.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(46, 103).addBox(-2.8942F, -1.2803F, -0.2978F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, 1.5F, 1.25F, -2.5683F, 0.0778F, 0.2575F));

		PartDefinition cube_r17 = hair_1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(56, 75).addBox(-0.8942F, -1.2803F, -1.2978F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.3F, 1.5F, 1.5F, -2.5683F, 0.0778F, 0.2575F));

		PartDefinition cube_r18 = hair_1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(106, 104).addBox(-1.8942F, -1.2803F, -1.2978F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -0.25F, 3.0F, -1.9735F, 0.2961F, 0.4048F));

		PartDefinition cube_r19 = hair_1.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(100, 91).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, -2.75F, 2.0F, -1.8021F, 0.2879F, 0.6009F));

		PartDefinition cube_r20 = hair_1.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(100, 91).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -5.5F, 1.5F, -1.6946F, -0.3272F, 0.3695F));

		PartDefinition cube_r21 = hair_1.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(100, 91).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -8.75F, 2.0F, -1.3526F, -0.3491F, 0.0F));

		PartDefinition cube_r22 = hair_1.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(38, 109).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -10.0F, 0.0F, -0.2182F, 0.0F, -1.4399F));

		PartDefinition cube_r23 = hair_1.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(68, 88).addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -12.0F, -0.25F, 0.0F, 0.0F, -1.0472F));

		PartDefinition cube_r24 = hair_1.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(96, 50).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -12.0F, -3.25F, 0.2618F, 0.0F, -1.0472F));

		PartDefinition cube_r25 = hair_1.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(94, 8).addBox(-0.8308F, -1.2113F, -1.0437F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -8.75F, -0.25F, 0.2463F, 0.2489F, 1.9965F));

		PartDefinition cube_r26 = hair_1.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(94, 8).addBox(-0.8308F, -1.2113F, -1.0437F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -10.25F, 0.25F, 0.0241F, 0.3475F, 1.2716F));

		PartDefinition cube_r27 = hair_1.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(94, 8).addBox(-0.8308F, -1.2113F, -1.0437F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -12.0F, 0.75F, 0.023F, 0.173F, 1.2674F));

		PartDefinition cube_r28 = hair_1.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(94, 8).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -13.0F, -1.25F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r29 = hair_1.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(94, 8).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -13.0F, -1.25F, 0.0F, 0.0F, -0.2618F));

		PartDefinition cube_r30 = hair_1.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(88, 19).addBox(-1.9364F, -1.3171F, -1.6933F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -5.5F, -3.0F, 0.0F, -0.1721F, 1.4411F));

		PartDefinition cube_r31 = hair_1.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(104, 33).addBox(1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, -7.5F, -4.0F, 0.0119F, -0.175F, 1.1753F));

		PartDefinition cube_r32 = hair_1.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(16, 96).addBox(-1.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -9.5F, -4.0F, -0.1217F, -0.1266F, 2.0131F));

		PartDefinition cube_r33 = hair_1.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(104, 33).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -11.0F, -4.0F, -0.0266F, -0.1734F, 1.3968F));

		PartDefinition cube_r34 = hair_1.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(104, 33).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -12.0F, -4.0F, 0.2194F, -0.2143F, 0.4999F));

		PartDefinition cube_r35 = hair_1.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(104, 33).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -12.0F, -3.5F, 0.3054F, 0.0F, -0.2618F));

		PartDefinition cube_r36 = hair_1.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(104, 33).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, -4.0F, 0.2182F, 0.0F, -1.5708F));

		PartDefinition hair_2 = hairs.addOrReplaceChild("hair_2", CubeListBuilder.create().texOffs(0, 90).addBox(-2.0F, -12.0F, -6.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(110, 41).addBox(-1.0F, -14.0F, -7.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(110, 41).addBox(-2.0F, -15.0F, -9.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(110, 41).addBox(-2.0F, -13.0F, -11.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(68, 41).addBox(-1.0F, -10.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 90).addBox(-1.5F, -14.0F, -5.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 90).addBox(-2.25F, -12.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(46, 34).addBox(-2.75F, -15.0F, 1.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(46, 0).addBox(-1.0F, -11.0F, -0.5F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(46, 13).addBox(-2.1F, -8.0F, -0.5F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(88, 11).addBox(3.75F, -8.0F, -4.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(80, 61).addBox(4.0F, -6.0F, -1.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 101).addBox(4.0F, -4.0F, -4.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(110, 51).addBox(4.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(110, 51).addBox(4.0F, 0.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hair_3 = hairs.addOrReplaceChild("hair_3", CubeListBuilder.create().texOffs(58, 104).addBox(-2.0F, -27.5F, -8.75F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.004F))
				.texOffs(102, 55).addBox(-1.0F, -27.5F, -8.75F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(104, 38).addBox(-1.0F, -27.5F, -4.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.5F, 5.0F));

		PartDefinition cube_r37 = hair_3.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(104, 110).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(5.0F, -16.0F, -7.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r38 = hair_3.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(70, 104).addBox(1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -16.0F, -4.75F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition cube_r39 = hair_3.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(80, 84).addBox(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -21.0F, -1.75F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition cube_r40 = hair_3.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(80, 77).addBox(0.0F, -1.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(4.0F, -23.5F, -1.75F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition cube_r41 = hair_3.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(78, 109).addBox(0.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -22.0F, -2.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r42 = hair_3.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 103).addBox(-1.0F, -2.0F, 1.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -23.0F, -3.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r43 = hair_3.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(80, 70).addBox(-1.0F, -1.0F, 0.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -27.0F, -2.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r44 = hair_3.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(0, 108).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.75F, -26.25F, -8.5F, 0.1931F, 0.3323F, -0.1582F));

		PartDefinition cube_r45 = hair_3.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(104, 18).addBox(-0.6558F, -0.694F, -1.9235F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -26.25F, -10.0F, 0.2848F, 0.2299F, -0.2892F));

		PartDefinition cube_r46 = hair_3.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(68, 109).addBox(-0.5F, -1.5195F, -2.1749F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -24.75F, -11.25F, 0.5909F, 0.1307F, -0.2965F));

		PartDefinition cube_r47 = hair_3.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(20, 111).addBox(0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(108, 98).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, -14.0F, 0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r48 = hair_3.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(16, 91).addBox(0.0F, -1.0F, -1.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -26.0F, -11.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition hair_4 = hairs.addOrReplaceChild("hair_4", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 5.0F));

		PartDefinition cube_r49 = hair_4.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(0, 112).addBox(-0.3305F, -0.1372F, -1.2095F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.25F, -20.25F, -11.0F, 0.2077F, -0.0279F, -1.5646F));

		PartDefinition cube_r50 = hair_4.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(40, 104).addBox(-0.852F, -1.5103F, -0.9052F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.75F, -20.0F, -11.5F, 0.2077F, -0.0279F, -2.7864F));

		PartDefinition cube_r51 = hair_4.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(40, 104).addBox(-0.852F, -1.5103F, -0.9052F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.75F, -18.0F, -12.0F, 0.0607F, 0.1336F, 2.3982F));

		PartDefinition cube_r52 = hair_4.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(40, 104).addBox(-0.852F, -1.5103F, -0.9052F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -17.0F, -11.0F, 0.0304F, 0.0784F, 1.3002F));

		PartDefinition cube_r53 = hair_4.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(40, 104).addBox(-0.852F, -1.5103F, -0.9052F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -17.0F, -13.0F, 0.0307F, 0.1656F, 1.3029F));

		PartDefinition cube_r54 = hair_4.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(110, 46).addBox(-0.852F, -1.5103F, -0.9052F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -18.0F, -12.75F, -0.1722F, -0.2125F, 0.4313F));

		PartDefinition cube_r55 = hair_4.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(112, 91).addBox(-0.852F, -1.5103F, -0.9052F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -21.0F, -12.5F, -0.1722F, -0.2125F, 0.1259F));

		PartDefinition cube_r56 = hair_4.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(52, 95).addBox(-0.852F, -1.5103F, -0.9052F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -23.75F, -12.25F, -0.1722F, -0.2125F, -0.1359F));

		PartDefinition cube_r57 = hair_4.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(52, 95).addBox(-0.852F, -1.5103F, -0.9052F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, -26.5F, -11.5F, -0.2015F, 0.0037F, -0.1328F));

		PartDefinition cube_r58 = hair_4.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(46, 98).addBox(0.6185F, 0.0558F, -2.6252F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -17.25F, -8.25F, 0.2115F, 0.0156F, -3.0382F));

		PartDefinition cube_r59 = hair_4.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(104, 8).addBox(-1.3815F, -0.9442F, -2.6252F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -20.0F, -7.25F, 0.2115F, 0.0156F, -3.0382F));

		PartDefinition cube_r60 = hair_4.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(96, 43).addBox(-1.3815F, -0.9442F, -2.6252F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -22.0F, -6.0F, 0.2057F, 0.0518F, 3.0741F));

		PartDefinition cube_r61 = hair_4.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(36, 85).addBox(-1.3815F, -0.9442F, -2.6252F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -24.75F, -5.0F, 0.2057F, 0.0518F, 3.0741F));

		PartDefinition cube_r62 = hair_4.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(36, 85).addBox(-1.3815F, -0.9442F, -2.6252F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -27.25F, -4.75F, 0.0603F, 0.0042F, -2.2498F));

		PartDefinition cube_r63 = hair_4.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(36, 85).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -30.0F, -10.5F, 0.2785F, 0.0042F, -2.2498F));

		PartDefinition cube_r64 = hair_4.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(96, 43).addBox(-1.3746F, -1.5185F, -2.0194F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -28.5F, -5.0F, -0.1191F, -0.017F, -1.1247F));

		PartDefinition cube_r65 = hair_4.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(96, 43).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -28.5F, -10.0F, 0.1186F, -0.1284F, -1.1421F));

		PartDefinition cube_r66 = hair_4.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -17.25F, -3.25F, 0.6788F, -0.3152F, -0.1526F));

		PartDefinition cube_r67 = hair_4.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -22.0F, -1.0F, -1.9199F, -0.5672F, -0.48F));

		PartDefinition cube_r68 = hair_4.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -2.0F, -1.9151F, -1.1989F, -0.2871F));

		PartDefinition cube_r69 = hair_4.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(100, 79).addBox(0.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.75F, -1.75F, -1.4465F, -0.51F, -0.2506F));

		PartDefinition cube_r70 = hair_4.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -18.0F, -5.25F, 0.3019F, -1.0444F, 1.4506F));

		PartDefinition cube_r71 = hair_4.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(68, 91).addBox(-1.0F, -0.672F, -3.7881F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -21.75F, -0.45F, -1.2911F, -0.0815F, 2.2388F));

		PartDefinition cube_r72 = hair_4.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -23.75F, -1.0F, -1.4962F, -0.6039F, 2.2014F));

		PartDefinition cube_r73 = hair_4.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(82, 104).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, -19.75F, -3.0F, -0.7108F, -0.6039F, 2.2014F));

		PartDefinition cube_r74 = hair_4.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(82, 104).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -21.75F, -1.0F, -0.9726F, -0.6039F, 2.2014F));

		PartDefinition cube_r75 = hair_4.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(68, 38).addBox(-1.0F, -1.672F, 0.2119F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -17.5F, -5.0F, 2.0943F, -0.0833F, -2.4717F));

		PartDefinition cube_r76 = hair_4.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(40, 104).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.579F, -15.6375F, -6.2946F, -2.6476F, 0.1988F, -2.9663F));

		PartDefinition cube_r77 = hair_4.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(40, 104).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.4678F, -14.6344F, -7.1936F, -2.7461F, -0.3617F, -1.9118F));

		PartDefinition cube_r78 = hair_4.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(40, 104).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0097F, -14.9014F, -7.364F, 2.9489F, -0.4963F, -0.6551F));

		PartDefinition cube_r79 = hair_4.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(12, 111).addBox(-1.0F, -1.672F, 0.2119F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -14.75F, -6.5F, 1.094F, -0.2396F, 0.0962F));

		PartDefinition cube_r80 = hair_4.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -18.65F, -6.5F, 1.094F, -0.2396F, 0.0962F));

		PartDefinition cube_r81 = hair_4.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -21.75F, -4.75F, 0.8593F, -0.7815F, -0.0252F));

		PartDefinition cube_r82 = hair_4.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -0.672F, -2.7881F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -21.75F, -3.0F, -0.3963F, -1.0444F, 1.4506F));

		PartDefinition cube_r83 = hair_4.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(100, 79).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -2.0F, 0.0F, -1.5708F, 0.6109F));

		PartDefinition cube_r84 = hair_4.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(82, 104).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -27.0F, -2.0F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition cube_r85 = hair_4.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(74, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -9.75F, 0.0F, 0.0F, -0.5236F));

		PartDefinition hair_5 = hairs.addOrReplaceChild("hair_5", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 5.0F));

		PartDefinition heldItem = head.addOrReplaceChild("heldItem", CubeListBuilder.create(), PartPose.offset(1.0F, -2.0F, -7.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -8.0F, 8.0F, 7.0F, 15.0F, new CubeDeformation(-0.26F))
				.texOffs(0, 22).addBox(-4.0F, -2.5F, -8.0F, 8.0F, 7.0F, 15.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, -0.7F, 2.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 6.0F));

		PartDefinition TailTop_r1 = tail.addOrReplaceChild("TailTop_r1", CubeListBuilder.create().texOffs(56, 81).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -1.1345F, 0.0F, 0.0F));

		PartDefinition Tail2L_r1 = tail.addOrReplaceChild("Tail2L_r1", CubeListBuilder.create().texOffs(0, 75).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.15F))
				.texOffs(64, 43).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 3.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition RWing = wings.addOrReplaceChild("RWing", CubeListBuilder.create().texOffs(88, 41).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-3.75F, -2.5F, -4.0F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition cube_r86 = RWing.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(32, 98).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-5.0F, 1.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r87 = RWing.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(0, 97).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-3.0F, 1.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r88 = RWing.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(48, 109).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition lowerWingR = RWing.addOrReplaceChild("lowerWingR", CubeListBuilder.create().texOffs(88, 41).addBox(-6.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.25F, 1.5F, 0.5F));

		PartDefinition cube_r89 = lowerWingR.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(98, 61).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(-0.75F, 0.5F, 2.25F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r90 = lowerWingR.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(28, 104).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(-4.25F, 0.5F, 0.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r91 = lowerWingR.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(94, 104).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-3.75F, 0.5F, 1.25F, 0.0F, -0.9163F, 0.0F));

		PartDefinition cube_r92 = lowerWingR.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(66, 98).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-2.75F, 0.5F, 2.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition LWing = wings.addOrReplaceChild("LWing", CubeListBuilder.create().texOffs(88, 41).addBox(-6.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(3.75F, -2.5F, -4.0F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition cube_r93 = LWing.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(32, 98).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r94 = LWing.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(0, 97).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r95 = LWing.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(48, 109).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 2.75F, 0.0F, -0.2182F, 0.0F));

		PartDefinition lowerWingR2 = LWing.addOrReplaceChild("lowerWingR2", CubeListBuilder.create().texOffs(88, 41).addBox(-6.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.25F, -1.5F, 0.5F));

		PartDefinition cube_r96 = lowerWingR2.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(98, 61).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(-0.75F, 0.5F, 2.25F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r97 = lowerWingR2.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(28, 104).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(-4.25F, 0.5F, 0.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r98 = lowerWingR2.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(94, 104).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-3.75F, 0.5F, 1.25F, 0.0F, -0.9163F, 0.0F));

		PartDefinition cube_r99 = lowerWingR2.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(66, 98).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-2.75F, 0.5F, 2.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition Arms = root.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -3.0F));

		PartDefinition rightArm = Arms.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 60).addBox(-2.0F, -10.75F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(-2.0F, 11.0F, -1.0F));

		PartDefinition rightSleeve = rightArm.addOrReplaceChild("rightSleeve", CubeListBuilder.create().texOffs(64, 58).addBox(-2.0F, -10.75F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftArm = Arms.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 60).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(2.0F, 11.25F, -1.0F));

		PartDefinition leftSleeve = leftArm.addOrReplaceChild("leftSleeve", CubeListBuilder.create().texOffs(72, 13).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Legs = root.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 7.0F));

		PartDefinition rightLeg = Legs.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(32, 60).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.25F))
				.texOffs(64, 73).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 11.25F, 0.0F));

		PartDefinition leftLeg = Legs.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(48, 60).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.25F))
				.texOffs(72, 28).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 11.25F, 0.0F));

		PartDefinition heldItemU = root.addOrReplaceChild("heldItemU", CubeListBuilder.create(), PartPose.offset(7.0F, 1.8F, -11.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	public boolean crouching;
	public float swimAmount;
	private int groundTickCount;
	private final Vector3f animationCache = new Vector3f();
	public final int maneStyles = 6;

	@Override
	public void setupAnim(AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		PonyData data = entity.getData(PonyAttachments.PONY_DATA);
		if (data.race()== PonyData.RACES.EARTH) {
			this.wings.visible = false;
			this.Horn.visible = false;
		} else if (data.race()== PonyData.RACES.PEGASUS) {
			this.wings.visible = true;
			this.Horn.visible = false;
		}else if (data.race()== PonyData.RACES.UNICORN) {
			this.wings.visible = false;
			this.Horn.visible = true;
		}
		for (int i=0;i<this.maneStyles;i++) {
			this.hairs.getChild("hair_"+i).visible= data.maneStyle() == i;
		}
		crouching =entity.isCrouching();
		swimAmount=entity.getSwimAmount(ageInTicks);
		groundTickCount =groundTick(entity, groundTickCount);

		PonyAnimationController controller = PonyAnimationManager.get(entity);
		controller.transitionTo(getDesiredAnimation(entity), ageInTicks);
		controller.apply(this, ageInTicks, animationCache);

		this.rotateHead(netHeadYaw, headPitch);
		if (!entity.onGround()) {
			limbSwingAmount/=2;
		}
		if (!entity.isFallFlying()) {
			if (entity.isSprinting()) {
				animateWalk(PonyAnimation.walk,limbSwing,limbSwingAmount,2f,1f);
			}
			animateWalk(PonyAnimation.walk,limbSwing,limbSwingAmount,2f,1f);
		}
		animate(PonyAnimationManager.attackType(entity),PonyAnimationManager.attackGet(entity),ageInTicks);
		PonyAnimationManager.stopAnimation(PonyAnimationManager.attackType(entity),PonyAnimationManager.attackGet(entity));
	}

	private int groundTick(AbstractClientPlayer player, int tick) {
		if (player.onGround()) {
			return ++tick;
		}
		return 0;
	}

	private void rotateHead(float netHeadYaw, float headPitch) {
		netHeadYaw = Math.clamp(netHeadYaw, -30, 30);
		headPitch = Math.clamp(headPitch, -30, 30);

		this.head.xRot = headPitch * (Mth.PI / 180);
		this.head.yRot = netHeadYaw * (Mth.PI/180);
	}

	private PonyAnimationLogic getDesiredAnimation(AbstractClientPlayer player) {
		if (player.isFallFlying()) {
			return PonyAnimationLogic.FLY;
		}

		if (player.fallDistance>0||!player.onGround()) {
			if (player.getDeltaMovement().y > 0.0D) {
				return PonyAnimationLogic.JUMP;
			}
			return PonyAnimationLogic.FALL;
		}

		if (groundTickCount < 20 ) {
			return PonyAnimationLogic.LAND;
		}
		if (player.isCrouching()) {
			return PonyAnimationLogic.CROUCH;
		}
		return PonyAnimationLogic.IDLE;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}