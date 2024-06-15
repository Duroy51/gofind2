import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">Location</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="lieu">Lieu</span>
          </dt>
          <dd>{locationEntity.lieu}</dd>
          <dt>
            <span id="prix">Prix</span>
          </dt>
          <dd>{locationEntity.prix}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{locationEntity.description}</dd>
          <dt>
            <span id="photoPieces">Photo Pieces</span>
          </dt>
          <dd>
            {locationEntity.photoPieces ? (
              <div>
                {locationEntity.photoPiecesContentType ? (
                  <a onClick={openFile(locationEntity.photoPiecesContentType, locationEntity.photoPieces)}>
                    <img
                      src={`data:${locationEntity.photoPiecesContentType};base64,${locationEntity.photoPieces}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {locationEntity.photoPiecesContentType}, {byteSize(locationEntity.photoPieces)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Utilisateur</dt>
          <dd>{locationEntity.utilisateur ? locationEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
