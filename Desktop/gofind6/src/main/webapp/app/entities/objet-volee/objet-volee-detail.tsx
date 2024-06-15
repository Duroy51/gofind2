import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './objet-volee.reducer';

export const ObjetVoleeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const objetVoleeEntity = useAppSelector(state => state.objetVolee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objetVoleeDetailsHeading">Objet Volee</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{objetVoleeEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{objetVoleeEntity.type}</dd>
          <dt>
            <span id="marque">Marque</span>
          </dt>
          <dd>{objetVoleeEntity.marque}</dd>
          <dt>
            <span id="datevol">Datevol</span>
          </dt>
          <dd>
            {objetVoleeEntity.datevol ? <TextFormat value={objetVoleeEntity.datevol} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="photoObjet">Photo Objet</span>
          </dt>
          <dd>
            {objetVoleeEntity.photoObjet ? (
              <div>
                {objetVoleeEntity.photoObjetContentType ? (
                  <a onClick={openFile(objetVoleeEntity.photoObjetContentType, objetVoleeEntity.photoObjet)}>
                    <img
                      src={`data:${objetVoleeEntity.photoObjetContentType};base64,${objetVoleeEntity.photoObjet}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {objetVoleeEntity.photoObjetContentType}, {byteSize(objetVoleeEntity.photoObjet)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="numeroSerie">Numero Serie</span>
          </dt>
          <dd>{objetVoleeEntity.numeroSerie}</dd>
          <dt>Utilisateur</dt>
          <dd>{objetVoleeEntity.utilisateur ? objetVoleeEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/objet-volee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/objet-volee/${objetVoleeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObjetVoleeDetail;
