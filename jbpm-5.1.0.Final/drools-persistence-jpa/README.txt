This project nearly corresponds to the original project 'drools-persistence-jpa' with version 5.3.0.Final. The 
difference is that this patch has a fixed beginCommandScopedEntityManager method in class JpaPersistenceContextManager:

old:
    public void beginCommandScopedEntityManager() {
        EntityManager cmdScopedEntityManager = (EntityManager) env.get( EnvironmentName.CMD_SCOPED_ENTITY_MANAGER );
        if ( cmdScopedEntityManager == null || 
           ( this.cmdScopedEntityManager != null && !this.cmdScopedEntityManager.isOpen() )) {
            internalCmdScopedEntityManager = true;
            this.cmdScopedEntityManager = this.emf.createEntityManager(); // no need to call joinTransaction as it will do so if one already exists
            this.cmdScopedEntityManager.setFlushMode(FlushModeType.COMMIT);
            this.env.set( EnvironmentName.CMD_SCOPED_ENTITY_MANAGER,
                          this.cmdScopedEntityManager );
            cmdScopedEntityManager = this.cmdScopedEntityManager;
        } else {
            internalCmdScopedEntityManager = false;
        }
        cmdScopedEntityManager.joinTransaction();
        appScopedEntityManager.joinTransaction();
    }

new:
	public void beginCommandScopedEntityManager() {
        this.cmdScopedEntityManager = (EntityManager) env.get( EnvironmentName.CMD_SCOPED_ENTITY_MANAGER );
        if ( cmdScopedEntityManager == null || 
           ( this.cmdScopedEntityManager != null && !this.cmdScopedEntityManager.isOpen() )) {
            internalCmdScopedEntityManager = true;
            this.cmdScopedEntityManager = this.emf.createEntityManager(); // no need to call joinTransaction as it will do so if one already exists
            this.cmdScopedEntityManager.setFlushMode(FlushModeType.COMMIT);
            this.env.set( EnvironmentName.CMD_SCOPED_ENTITY_MANAGER,
                          this.cmdScopedEntityManager );
            this.cmdScopedEntityManager = this.cmdScopedEntityManager;
        } else {
            internalCmdScopedEntityManager = false;
        }
        this.cmdScopedEntityManager.joinTransaction();
        appScopedEntityManager.joinTransaction();
    }