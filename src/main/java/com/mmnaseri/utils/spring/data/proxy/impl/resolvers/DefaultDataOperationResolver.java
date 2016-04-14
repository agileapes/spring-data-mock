package com.mmnaseri.utils.spring.data.proxy.impl.resolvers;

import com.mmnaseri.utils.spring.data.domain.RepositoryMetadata;
import com.mmnaseri.utils.spring.data.domain.impl.QueryDescriptionExtractor;
import com.mmnaseri.utils.spring.data.error.DataOperationDefinitionException;
import com.mmnaseri.utils.spring.data.error.UnknownDataOperationException;
import com.mmnaseri.utils.spring.data.proxy.DataOperationResolver;
import com.mmnaseri.utils.spring.data.proxy.RepositoryFactoryConfiguration;
import com.mmnaseri.utils.spring.data.proxy.TypeMapping;
import com.mmnaseri.utils.spring.data.query.DataFunctionRegistry;
import com.mmnaseri.utils.spring.data.store.DataStoreOperation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will use the other resolvers to find out how a data operation should be handled.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/29/15)
 */
public class DefaultDataOperationResolver implements DataOperationResolver {

    private final List<DataOperationResolver> resolvers;

    public DefaultDataOperationResolver(List<TypeMapping<?>> implementations, QueryDescriptionExtractor descriptionExtractor, RepositoryMetadata repositoryMetadata, DataFunctionRegistry functionRegistry, RepositoryFactoryConfiguration configuration) {
        resolvers = new ArrayList<>();
        resolvers.add(new SignatureDataOperationResolver(implementations));
        resolvers.add(new QueryMethodDataOperationResolver(descriptionExtractor, repositoryMetadata, functionRegistry, configuration));
    }

    @Override
    public DataStoreOperation<?, ?, ?> resolve(Method method) {
        for (DataOperationResolver resolver : resolvers) {
            final DataStoreOperation<?, ?, ?> resolution;
            try {
                resolution = resolver.resolve(method);
            } catch (Exception e) {
                throw new DataOperationDefinitionException(method, e);
            }
            if (resolution != null) {
                return resolution;
            }
        }
        throw new UnknownDataOperationException(method);
    }

}
