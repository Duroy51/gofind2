import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import trajet from 'app/entities/trajet/trajet.reducer';
import location from 'app/entities/location/location.reducer';
import objetVolee from 'app/entities/objet-volee/objet-volee.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  utilisateur,
  trajet,
  location,
  objetVolee,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
