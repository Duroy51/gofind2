import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/utilisateur">
        Utilisateur
      </MenuItem>
      <MenuItem icon="asterisk" to="/annonce">
        Annonce
      </MenuItem>
      <MenuItem icon="asterisk" to="/trajet">
        Trajet
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        Location
      </MenuItem>
      <MenuItem icon="asterisk" to="/declaration-vol">
        Declaration Vol
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
