import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './annonce.reducer';

export const AnnonceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const annonceEntity = useAppSelector(state => state.annonce.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="annonceDetailsHeading">Annonce</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{annonceEntity.id}</dd>
          <dt>
            <span id="titre">Titre</span>
          </dt>
          <dd>{annonceEntity.titre}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{annonceEntity.description}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{annonceEntity.type}</dd>
          <dt>
            <span id="statut">Statut</span>
          </dt>
          <dd>{annonceEntity.statut}</dd>
          <dt>
            <span id="datecreation">Datecreation</span>
          </dt>
          <dd>
            {annonceEntity.datecreation ? <TextFormat value={annonceEntity.datecreation} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Trajet</dt>
          <dd>{annonceEntity.trajet ? annonceEntity.trajet.id : ''}</dd>
          <dt>Location</dt>
          <dd>{annonceEntity.location ? annonceEntity.location.id : ''}</dd>
          <dt>Declaration Vol</dt>
          <dd>{annonceEntity.declarationVol ? annonceEntity.declarationVol.id : ''}</dd>
          <dt>Utilisateur</dt>
          <dd>{annonceEntity.utilisateur ? annonceEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/annonce" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/annonce/${annonceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnnonceDetail;
