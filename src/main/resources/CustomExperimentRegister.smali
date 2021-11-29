.class public Lio/github/jroy/experiment/CustomExperimentRegister;
.super Ljava/lang/Object;
.source "CustomExperimentRegister.java"


# direct methods
.method static constructor <clinit>()V
    .registers 0

    .prologue
    .line 10
    invoke-static {}, Lio/github/jroy/experiment/CustomExperimentRegister;->register()V

    .line 11
    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static register()V
    .registers 14

    .prologue
    const/4 v13, 0x3

    const/4 v12, 0x2

    const/4 v11, 0x0

    const/4 v5, 0x1

    .line 14
    const/4 v6, 0x1

    .line 15
    .local v6, "i":I
    const/4 v7, 0x2

    .line 19
    .local v7, "i2":I
    sget-object v0, Lcom/discord/utilities/experiments/ExperimentRegistry;->INSTANCE:Lcom/discord/utilities/experiments/ExperimentRegistry;

    invoke-virtual {v0}, Lcom/discord/utilities/experiments/ExperimentRegistry;->getRegisteredExperiments()Ljava/util/LinkedHashMap;

    move-result-object v8

    const-string v9, "2021-01_google_smart_lock"

    new-instance v0, Lcom/discord/utilities/experiments/RegisteredExperiment;

    const-string v1, "[C]: Smart Lock"

    const-string v2, "2021-01_google_smart_lock"

    sget-object v3, Lcom/discord/utilities/experiments/RegisteredExperiment$Type;->USER:Lcom/discord/utilities/experiments/RegisteredExperiment$Type;

    new-array v4, v12, [Ljava/lang/String;

    const-string v10, "Control"

    aput-object v10, v4, v11

    const-string v10, "Treatment 1: Enable Google Smart Lock Support."

    aput-object v10, v4, v5

    .line 20
    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    invoke-direct/range {v0 .. v5}, Lcom/discord/utilities/experiments/RegisteredExperiment;-><init>(Ljava/lang/String;Ljava/lang/String;Lcom/discord/utilities/experiments/RegisteredExperiment$Type;Ljava/util/List;Z)V

    .line 19
    invoke-virtual {v8, v9, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 21
    sget-object v0, Lcom/discord/utilities/experiments/ExperimentRegistry;->INSTANCE:Lcom/discord/utilities/experiments/ExperimentRegistry;

    invoke-virtual {v0}, Lcom/discord/utilities/experiments/ExperimentRegistry;->getRegisteredExperiments()Ljava/util/LinkedHashMap;

    move-result-object v8

    const-string v9, "2021-08_guild_role_subscription_users"

    new-instance v0, Lcom/discord/utilities/experiments/RegisteredExperiment;

    const-string v1, "[C]: User Role Subscriptions"

    const-string v2, "2021-08_guild_role_subscription_users"

    sget-object v3, Lcom/discord/utilities/experiments/RegisteredExperiment$Type;->USER:Lcom/discord/utilities/experiments/RegisteredExperiment$Type;

    new-array v4, v12, [Ljava/lang/String;

    const-string v10, "Control"

    aput-object v10, v4, v11

    const-string v10, "Treatment 1: Enable Creating Role Subscriptions."

    aput-object v10, v4, v5

    .line 22
    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    invoke-direct/range {v0 .. v5}, Lcom/discord/utilities/experiments/RegisteredExperiment;-><init>(Ljava/lang/String;Ljava/lang/String;Lcom/discord/utilities/experiments/RegisteredExperiment$Type;Ljava/util/List;Z)V

    .line 21
    invoke-virtual {v8, v9, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 23
    sget-object v0, Lcom/discord/utilities/experiments/ExperimentRegistry;->INSTANCE:Lcom/discord/utilities/experiments/ExperimentRegistry;

    invoke-virtual {v0}, Lcom/discord/utilities/experiments/ExperimentRegistry;->getRegisteredExperiments()Ljava/util/LinkedHashMap;

    move-result-object v8

    const-string v9, "2021-06_guild_role_subscriptions"

    new-instance v0, Lcom/discord/utilities/experiments/RegisteredExperiment;

    const-string v1, "[C]: Guild Role Subscriptions"

    const-string v2, "2021-06_guild_role_subscriptions"

    sget-object v3, Lcom/discord/utilities/experiments/RegisteredExperiment$Type;->USER:Lcom/discord/utilities/experiments/RegisteredExperiment$Type;

    new-array v4, v12, [Ljava/lang/String;

    const-string v10, "Control"

    aput-object v10, v4, v11

    const-string v10, "Treatment 1: Enable Creating Role Subscriptions."

    aput-object v10, v4, v5

    .line 24
    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    invoke-direct/range {v0 .. v5}, Lcom/discord/utilities/experiments/RegisteredExperiment;-><init>(Ljava/lang/String;Ljava/lang/String;Lcom/discord/utilities/experiments/RegisteredExperiment$Type;Ljava/util/List;Z)V

    .line 23
    invoke-virtual {v8, v9, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 25
    sget-object v0, Lcom/discord/utilities/experiments/ExperimentRegistry;->INSTANCE:Lcom/discord/utilities/experiments/ExperimentRegistry;

    invoke-virtual {v0}, Lcom/discord/utilities/experiments/ExperimentRegistry;->getRegisteredExperiments()Ljava/util/LinkedHashMap;

    move-result-object v8

    const-string v9, "2021-06_hub_discovery"

    new-instance v0, Lcom/discord/utilities/experiments/RegisteredExperiment;

    const-string v1, "[C]: Hub Discovery"

    const-string v2, "2021-06_hub_discovery"

    sget-object v3, Lcom/discord/utilities/experiments/RegisteredExperiment$Type;->USER:Lcom/discord/utilities/experiments/RegisteredExperiment$Type;

    new-array v4, v13, [Ljava/lang/String;

    const-string v10, "Control"

    aput-object v10, v4, v11

    const-string v10, "Treatment 1: Enable Hub Discovery + Sparkle CTA."

    aput-object v10, v4, v5

    const-string v10, "Treatment 2: Enable Hub Discovery"

    aput-object v10, v4, v12

    .line 26
    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    invoke-direct/range {v0 .. v5}, Lcom/discord/utilities/experiments/RegisteredExperiment;-><init>(Ljava/lang/String;Ljava/lang/String;Lcom/discord/utilities/experiments/RegisteredExperiment$Type;Ljava/util/List;Z)V

    .line 25
    invoke-virtual {v8, v9, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 27
    return-void
.end method
