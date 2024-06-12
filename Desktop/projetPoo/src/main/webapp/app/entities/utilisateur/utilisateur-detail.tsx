import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './utilisateur.reducer';

export const UtilisateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="utilisateurDetailsHeading">Utilisateur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{utilisateurEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{utilisateurEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{utilisateurEntity.prenom}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{utilisateurEntity.email}</dd>
          <dt>
            <span id="telephone">Telephone</span>
          </dt>
          <dd>{utilisateurEntity.telephone}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{utilisateurEntity.password}</dd>
        </dl>
        <Button tag={Link} to="/utilisateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UtilisateurDetail;
