import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import annonce from 'app/entities/annonce/annonce.reducer';
import trajet from 'app/entities/trajet/trajet.reducer';
import location from 'app/entities/location/location.reducer';
import declarationVol from 'app/entities/declaration-vol/declaration-vol.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  utilisateur,
  annonce,
  trajet,
  location,
  declarationVol,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
